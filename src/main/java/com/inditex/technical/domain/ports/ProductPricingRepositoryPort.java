package com.inditex.technical.domain.ports;

import java.time.LocalDateTime;
import java.util.Optional;

import com.inditex.technical.domain.model.ProductPricing;

public interface ProductPricingRepositoryPort {

    Optional<ProductPricing> findPrice(LocalDateTime startDate, Long productId, Long brandId);
    
}
