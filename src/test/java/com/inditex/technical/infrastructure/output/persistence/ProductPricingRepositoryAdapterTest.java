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

import com.inditex.technical.domain.model.ProductPricing;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class ProductPricingRepositoryAdapterTest {

    @Autowired
    private ProductPricingRepositoryAdapter repositoryAdapter;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    
    @Test
    void testFindPriceNull() {
        LocalDateTime requestDate = LocalDateTime.of(2025, 6, 14, 10, 0, 0);
        assertThrows(EntityNotFoundException.class, () -> {
            repositoryAdapter.findPrice(requestDate, PRODUCT_ID, BRAND_ID);
        });
    }

    @ParameterizedTest
    @CsvSource({
        "2020-06-14T10:00:00, 35.50",
        "2020-06-14T16:00:00, 25.45",
        "2020-06-14T21:00:00, 35.50",
        "2020-06-15T10:00:00, 30.50",
        "2020-06-16T21:00:00, 38.95"
    })
    void testFindPriceAtVariousTimes(String dateTimeString, BigDecimal expectedPrice) {

        LocalDateTime requestDate = LocalDateTime.parse(dateTimeString);

        ProductPricing result = repositoryAdapter.findPrice(requestDate, PRODUCT_ID, BRAND_ID);

        assertNotNull(result);
        assertEquals(expectedPrice, result.getPrice());
    }
}