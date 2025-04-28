package com.inditex.technical.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.infrastructure.dto.ProductPricingResponse;
import com.inditex.technical.infrastructure.output.persistence.ProductPricingEntity;

class ProductPricingMapperTest {

    private final ProductPricingMapper mapper = Mappers.getMapper(ProductPricingMapper.class);
    private LocalDateTime now = LocalDateTime.now();

    private ProductPricingResponse getSampleProductPricingDTO() {
        ProductPricingResponse dto = new ProductPricingResponse();
        dto.setBrandId(1L);
        dto.setStartDate(now);
        dto.setEndDate(now.plusDays(1));
        dto.setPriceList(2L);
        dto.setProductId(3L);
        dto.setPrice(new BigDecimal("99.99"));
        dto.setCurrency("EUR");
        return dto;
    }

    private ProductPricing getSampleProductPricingDomain() {
        return ProductPricing.builder()
                .brandId(1L)
                .startDate(now)
                .endDate(now.plusDays(1))
                .priceList(2L)
                .productId(3L)
                .price(new BigDecimal("99.99"))
                .currency("EUR")
                .build();
    }

    private ProductPricingEntity getSampleProductPricingEntity() {
        return new ProductPricingEntity(
            1L, 1L, now, now.plusDays(1), 2, 3L, 1, new BigDecimal("99.99"), "EUR"
        );
    }

    private ProductPricing dtoToDomain(ProductPricingResponse dto) {
        return ProductPricing.builder()
                .brandId(dto.getBrandId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .priceList(dto.getPriceList())
                .productId(dto.getProductId())
                .price(dto.getPrice())
                .currency(dto.getCurrency())
                .build();
    }

    private ProductPricingResponse domainToDTO(ProductPricing domain) {
        ProductPricingResponse dto = new ProductPricingResponse();
        dto.setBrandId(domain.getBrandId());
        dto.setStartDate(domain.getStartDate());
        dto.setEndDate(domain.getEndDate());
        dto.setPriceList(domain.getPriceList());
        dto.setProductId(domain.getProductId());
        dto.setPrice(domain.getPrice());
        dto.setCurrency(domain.getCurrency());
        return dto;
    }

    private ProductPricing entityToDomain(ProductPricingEntity entity) {
        return ProductPricing.builder()
                .brandId(entity.getBrandId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList((long) entity.getPriceList())
                .productId(entity.getProductId())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }

    private void assertProductPricingEquals(ProductPricing expected, ProductPricing actual) {
        assertNotNull(actual);
        assertEquals(expected.getBrandId(), actual.getBrandId());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getPriceList(), actual.getPriceList());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCurrency(), actual.getCurrency());
    }

    private void assertProductPricingDTOEquals(ProductPricingResponse expected, ProductPricingResponse actual) {
        assertNotNull(actual);
        assertEquals(expected.getBrandId(), actual.getBrandId());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getPriceList(), actual.getPriceList());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCurrency(), actual.getCurrency());
    }

    @Test
    void testFromDTOToDomain() {
        ProductPricingResponse dto = getSampleProductPricingDTO();
        ProductPricing domain = mapper.fromDtoToDomain(dto);
        assertProductPricingEquals(dtoToDomain(dto), domain);  // Comparando DTO con el dominio
    }

    @Test
    void testToDto() {
        ProductPricing domain = getSampleProductPricingDomain();
        ProductPricingResponse dto = mapper.fromDomainToDto(domain);
        assertProductPricingDTOEquals(domainToDTO(domain), dto);  // Comparando Domain con el DTO
    }

    @Test
    void testToDomainFromEntity() {
        ProductPricingEntity entity = getSampleProductPricingEntity();
        ProductPricing domain = mapper.fromEntityToDomain(entity);
        assertProductPricingEquals(entityToDomain(entity), domain);  // Comparando Entity con el dominio
    }

    @Test
    void testToEntity() {
        ProductPricing domain = getSampleProductPricingDomain();
        ProductPricingEntity entity = mapper.fromDomainToEntity(domain);
        assertNotNull(entity);
        assertEquals(domain.getBrandId(), entity.getBrandId());
        assertEquals(domain.getStartDate(), entity.getStartDate());
        assertEquals(domain.getEndDate(), entity.getEndDate());
        assertEquals(domain.getPriceList().intValue(), entity.getPriceList());
        assertEquals(domain.getProductId(), entity.getProductId());
        assertEquals(domain.getPrice(), entity.getPrice());
        assertEquals(domain.getCurrency(), entity.getCurrency());
    }

    @Test
    void testNullInputs() {
        assertNull(mapper.fromDtoToDomain(null));
        assertNull(mapper.fromDomainToDto(null));
        assertNull(mapper.fromEntityToDomain(null));
        assertNull(mapper.fromDomainToEntity(null));
    }

    @Test
    void testPartialNullFields() {
        ProductPricingResponse dto = new ProductPricingResponse();
        dto.setBrandId(1L);
        dto.setPrice(new BigDecimal("99.99"));
        ProductPricing domain = mapper.fromDtoToDomain(dto);
        assertNotNull(domain);
        assertEquals(dto.getBrandId(), domain.getBrandId());
        assertEquals(dto.getPrice(), domain.getPrice());
        assertNull(domain.getStartDate());
        assertNull(domain.getEndDate());
        assertNull(domain.getPriceList());
        assertNull(domain.getProductId());
        assertNull(domain.getCurrency());
    }

}
