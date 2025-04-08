package com.inditex.technical.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.inditex.technical.application.dto.ProductPricingDTO;
import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.infrastructure.output.persistence.ProductPricingEntity;

@Mapper(componentModel = "spring")
public interface ProductPricingMapper {

    ProductPricing fromDTOToDomain(ProductPricingDTO dto);
    ProductPricingDTO toDto(ProductPricing domain);

    ProductPricing toDomain(ProductPricingEntity entity);
    
    @Mapping(target = "id", ignore = true)
    ProductPricingEntity toEntity(ProductPricing domain);

}
