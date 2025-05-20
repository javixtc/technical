package com.inditex.technical.infrastructure.output.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.inditex.technical.domain.exceptions.PriceNotFoundException;
import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.domain.ports.ProductPricingServicePort;

@SpringBootTest
@Transactional
class ProductPricingRepositoryAdapterTest {

    @Autowired
    private ProductPricingServicePort service;

    @ParameterizedTest
    @CsvSource({
        "2020-06-14T10:00:00, 35.50",
        "2020-06-14T16:00:00, 25.45",
        "2020-06-14T21:00:00, 35.50",
        "2020-06-15T10:00:00, 30.50",
        "2020-06-16T21:00:00, 38.95",
    })
    void testFindPriceFromUseCase(String dateTimeString, BigDecimal expectedPrice) {
        LocalDateTime date = LocalDateTime.parse(dateTimeString);
        Long productId = 35455L;
        Long brandId = 1L;

        ProductPricing result = service.findPrice(date, productId, brandId);

        assertNotNull(result);
        assertEquals(expectedPrice, result.getPrice());
    }

    @Test
    void testFindPriceFromUseCase_NotFound_ThrowsException() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);
        Long nonExistingProductId = 99999L;
        Long nonExistingBrandId = 999L;

        assertThrows(PriceNotFoundException.class, () -> {
            service.findPrice(date, nonExistingProductId, nonExistingBrandId);
        });
    }
}