package com.inditex.technical.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inditex.technical.domain.exceptions.PriceNotFoundException;
import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.domain.ports.ProductPricingRepositoryPort;
import com.inditex.technical.domain.ports.ProductPricingServicePort;

@Service
public class FindProductPricingUseCase implements ProductPricingServicePort {

    private final ProductPricingRepositoryPort repositoryPort;

    public FindProductPricingUseCase(ProductPricingRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public ProductPricing findPrice(LocalDateTime startDate, Long productId, Long brandId) {
        return this.repositoryPort.findPrice(startDate, productId, brandId)
            .orElseThrow(() -> new PriceNotFoundException("Precio no encontrado para productId: " + productId + ", brandId: " + brandId + ", fecha: " + startDate));
    }
}