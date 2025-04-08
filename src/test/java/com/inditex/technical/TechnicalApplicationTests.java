package com.inditex.technical;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechnicalApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    void mainMethodDoesNotThrowException() {
        assertDoesNotThrow(() -> TechnicalApplication.main(new String[]{}));
    }

}
