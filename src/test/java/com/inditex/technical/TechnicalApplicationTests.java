package com.inditex.technical;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechnicalApplicationTests {

	/**
	 * This test ensures that the main method starts the application without throwing exceptions.
	 * Used to cover 100% of the code coverage.
	 */
	@Test
    void mainMethodDoesNotThrowException() {
        assertDoesNotThrow(() -> TechnicalApplication.main(new String[]{}));
    }

}
