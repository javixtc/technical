package com.inditex.technical.infrastructure.output.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.domain.ports.ProductPricingRepositoryPort;
import com.inditex.technical.infrastructure.mapper.ProductPricingMapper;

@Component
public class ProductPricingRepositoryAdapter implements ProductPricingRepositoryPort {

    private final ProductPricingJpaRepository jpaRepository;
    private final ProductPricingMapper productPricingMapper;

    public ProductPricingRepositoryAdapter(ProductPricingJpaRepository jpaRepository, ProductPricingMapper productPricingMapper) {
        this.jpaRepository = jpaRepository;
        this.productPricingMapper = productPricingMapper;
    }

    @Override
    public Optional<ProductPricing> findPrice(LocalDateTime startDate, Long productId, Long brandId) {
        ProductPricingEntity productPricingEntity = this.jpaRepository.findPrice(startDate, productId, brandId);
        return productPricingEntity != null 
            ? Optional.ofNullable(this.productPricingMapper.fromEntityToDomain(productPricingEntity)) 
            : Optional.empty();
    }
}
