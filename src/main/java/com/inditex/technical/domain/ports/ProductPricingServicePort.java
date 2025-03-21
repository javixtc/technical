package com.inditex.technical.domain.ports;

import java.time.LocalDateTime;

import com.inditex.technical.domain.model.ProductPricing;

public interface ProductPricingServicePort {
    
    ProductPricing findPrice(LocalDateTime startDate, Long productId, Long brandId);

}
