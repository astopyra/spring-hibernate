package com.kodilla.csvconverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.batch.jdbc.initialize-schema=always")
class CsvConverterApplicationTests {
	@Test
	void contextLoads() {
	}
}
