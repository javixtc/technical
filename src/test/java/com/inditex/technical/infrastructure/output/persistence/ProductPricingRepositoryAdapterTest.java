package com.inditex.technical.infrastructure.output.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.inditex.technical.domain.model.ProductPricing;

@SpringBootTest
@Transactional
class ProductPricingRepositoryAdapterTest {

    @Autowired
    private ProductPricingRepositoryAdapter repositoryAdapter;

    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @ParameterizedTest
    @CsvSource({
        "2020-06-14T10:00:00, 35.50",
        "2020-06-14T16:00:00, 25.45",
        "2020-06-14T21:00:00, 35.50",
        "2020-06-15T10:00:00, 30.50",
        "2020-06-16T21:00:00, 38.95",
    })
    void testFindPriceAtVariousTimes(String dateTimeString, BigDecimal expectedPrice) {

        LocalDateTime requestDate = LocalDateTime.parse(dateTimeString);

        Optional<ProductPricing> result = repositoryAdapter.findPrice(requestDate, PRODUCT_ID, BRAND_ID);

        assertNotNull(result);
        assertEquals(expectedPrice, result.get().getPrice());
    }

    @Test
    void testFindPriceNoResultReturnsEmptyOptional() {

        LocalDateTime requestDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        Long nonExistingProductId = 99999L;
        Long nonExistingBrandId = 999L;

        Optional<ProductPricing> result = repositoryAdapter.findPrice(requestDate, nonExistingProductId, nonExistingBrandId);

        assertNotNull(result);
        assertEquals(Optional.empty(), result);
    }
}