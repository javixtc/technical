package com.inditex.technical.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inditex.technical.domain.exceptions.PriceNotFoundException;
import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.domain.ports.ProductPricingRepositoryPort;

@ExtendWith(MockitoExtension.class)
class FindProductPricingUseCaseTest {

    @Mock
    private ProductPricingRepositoryPort repositoryPort;

    @InjectMocks
    private FindProductPricingUseCase useCase;

    @Test
    void testFindPrice_Success() {
        LocalDateTime now = LocalDateTime.now();
        ProductPricing expectedProductPricing = ProductPricing.builder()
                .brandId(1L)
                .startDate(now)
                .endDate(now.plusDays(30))
                .priceList(2L)
                .productId(3L)
                .priority(1)
                .price(new BigDecimal("19.99"))
                .currency("USD")
                .build();
        
        when(repositoryPort.findPrice(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(Optional.of(expectedProductPricing));

        ProductPricing result = useCase.findPrice(now, 35455L, 1L);

        assertNotNull(result);
        assertEquals(expectedProductPricing.getBrandId(), result.getBrandId());
        assertEquals(expectedProductPricing.getStartDate(), result.getStartDate());
        assertEquals(expectedProductPricing.getEndDate(), result.getEndDate());
        assertEquals(expectedProductPricing.getPriceList(), result.getPriceList());
        assertEquals(expectedProductPricing.getProductId(), result.getProductId());
        assertEquals(expectedProductPricing.getPriority(), result.getPriority());
        assertEquals(expectedProductPricing.getPrice(), result.getPrice());
        assertEquals(expectedProductPricing.getCurrency(), result.getCurrency());
    }

    @Test
    void testFindPrice_NotFound_ShouldThrowException() {
        LocalDateTime now = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;

        when(repositoryPort.findPrice(any(LocalDateTime.class), any(Long.class), any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> useCase.findPrice(now, productId, brandId));
    }


}

