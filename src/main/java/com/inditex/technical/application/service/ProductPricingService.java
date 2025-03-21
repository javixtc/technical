package com.inditex.technical.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.domain.ports.ProductPricingRepositoryPort;
import com.inditex.technical.domain.ports.ProductPricingServicePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPricingService implements ProductPricingServicePort {

    private final ProductPricingRepositoryPort productPricingRepositoryPort;

    @Override
    public ProductPricing findPrice(LocalDateTime startDate, Long productId, Long brandId) {
        return productPricingRepositoryPort.findPrice(startDate, productId, brandId);
    }

}
