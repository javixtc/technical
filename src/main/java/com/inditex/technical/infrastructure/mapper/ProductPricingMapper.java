package com.inditex.technical.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.infrastructure.dto.ProductPricingResponse;
import com.inditex.technical.infrastructure.output.persistence.ProductPricingEntity;

@Mapper(componentModel = "spring")
public interface ProductPricingMapper {

    @Mapping(target = "priority", ignore = true)
    ProductPricing fromDtoToDomain(ProductPricingResponse dto);    
    
    ProductPricingResponse fromDomainToDto(ProductPricing domain);

    ProductPricing fromEntityToDomain(ProductPricingEntity entity);
    
    @Mapping(target = "id", ignore = true)
    ProductPricingEntity fromDomainToEntity(ProductPricing domain);

}
