package com.kodilla.integration;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {

    private final Path inputDir = Paths.get("data/input");
    private final Path logFile = Paths.get("data/output/log.txt");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(inputDir);
        Files.createDirectories(logFile.getParent());
        if (!Files.exists(logFile)) {
            Files.createFile(logFile);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFile(@RequestParam String fileName, @RequestBody String content) {
        try {
            Path filePath = inputDir.resolve(fileName);
            Files.write(filePath, content.getBytes());
            return ResponseEntity.ok("File created: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/log")
    public ResponseEntity<String> getLogFile() {
        try {
            String content = Files.readString(logFile);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
