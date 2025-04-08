package com.inditex.technical.infrastructure.output.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.infrastructure.mapper.ProductPricingMapper;

import jakarta.persistence.EntityNotFoundException;

class ProductPricingRepositoryAdapterUnitTest {

    @Mock
    private ProductPricingJpaRepository jpaRepository;

    @Spy
    private ProductPricingMapper productPricingMapper = Mappers.getMapper(ProductPricingMapper.class);

    @InjectMocks
    private ProductPricingRepositoryAdapter repositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPrice_ThrowsEntityNotFoundException_WhenNoPriceFound() {
        LocalDateTime requestDate = LocalDateTime.of(2099, 12, 31, 23, 59, 59);
        when(jpaRepository.findPrice(any(), anyLong(), anyLong())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            repositoryAdapter.findPrice(requestDate, 35455L, 1L);
        });
    }

    @Test
    void testFindPrice_ReturnsValidPrice() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        ProductPricingEntity entity = new ProductPricingEntity(
            1L, 
            1L, 
            startDate, 
            endDate, 
            1, 
            35455L, 
            0, 
            new BigDecimal("35.50"), 
            "EUR"
        );

        when(jpaRepository.findPrice(any(), anyLong(), anyLong())).thenReturn(entity);

        ProductPricing result = repositoryAdapter.findPrice(startDate, 35455L, 1L);

        assertNotNull(result);
        assertEquals(new BigDecimal("35.50"), result.getPrice());
    }
}
