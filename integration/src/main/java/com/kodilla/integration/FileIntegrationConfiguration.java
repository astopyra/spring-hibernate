package com.kodilla.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class FileIntegrationConfiguration {
    @Bean
    IntegrationFlow fileIntegrationFlow(FileReadingMessageSource fileAdapter) {
        return IntegrationFlow.from(fileAdapter,
                        config -> config.poller(Pollers.fixedDelay(1000)))
                .transform(File.class, File::getName)
                .handle(logFileNameHandler())
                .get();
    }

    @Bean
    FileReadingMessageSource fileAdapter() {
        FileReadingMessageSource fileSource = new FileReadingMessageSource();
        fileSource.setDirectory(new File("data/input"));
        return fileSource;
    }

    @Bean
    MessageHandler logFileNameHandler() {
        return message -> {
            String fileName = (String) message.getPayload();
            File outputFile = new File("data/output/log.txt");
            outputFile.getParentFile().mkdirs();
            try (FileWriter fw = new FileWriter(outputFile, true)) { // append = true
                fw.write(fileName + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
