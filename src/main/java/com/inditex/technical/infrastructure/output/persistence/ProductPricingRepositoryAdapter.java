package com.inditex.technical.infrastructure.output.persistence;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.domain.ports.ProductPricingRepositoryPort;
import com.inditex.technical.infrastructure.mapper.ProductPricingMapper;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ProductPricingRepositoryAdapter implements ProductPricingRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(ProductPricingRepositoryAdapter.class);

    private final ProductPricingJpaRepository jpaRepository;
    private final ProductPricingMapper productPricingMapper;

    public ProductPricingRepositoryAdapter(ProductPricingJpaRepository jpaRepository, ProductPricingMapper productPricingMapper) {
        this.jpaRepository = jpaRepository;
        this.productPricingMapper = productPricingMapper;
    }

    @Override
    public ProductPricing findPrice(LocalDateTime startDate, Long productId, Long brandId) {
        ProductPricingEntity productPricingEntity = jpaRepository.findPrice(startDate, productId, brandId);
        if (productPricingEntity == null) {
            logger.error("Precio no encontrado para productId: {} y brandId: {} a las {}", productId, brandId, startDate);
            throw new EntityNotFoundException("Precio no encontrado");
        }
        return productPricingMapper.toDomain(productPricingEntity);
    }
}
