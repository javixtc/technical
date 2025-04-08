package com.inditex.technical.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        ProductPricing productPricing = ProductPricing.builder()
                .brandId(1L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .priceList(2L)
                .productId(3L)
                .priority(1)
                .price(new BigDecimal("19.99"))
                .currency("USD")
                .build();
        when(repositoryPort.findPrice(any(), any(), any())).thenReturn(productPricing);

        ProductPricing result = useCase.findPrice(LocalDateTime.now(), 35455L, 1L);
        
        assertNotNull(result);
    }
}

