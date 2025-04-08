package com.inditex.technical.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.inditex.technical.application.dto.ProductPricingDTO;
import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.infrastructure.output.persistence.ProductPricingEntity;

class ProductPricingMapperTest {

    private final ProductPricingMapper mapper = Mappers.getMapper(ProductPricingMapper.class);

    private LocalDateTime now = LocalDateTime.now();
    private ProductPricingDTO getSampleProductPricingDTO() {
        ProductPricingDTO dto = new ProductPricingDTO();
        dto.setBrandId(1L);
        dto.setStartDate(now);
        dto.setEndDate(now.plusDays(1));
        dto.setPriceList(2L);
        dto.setProductId(3L);
        dto.setPriority(1);
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
                .priority(1)
                .price(new BigDecimal("99.99"))
                .currency("EUR")
                .build();
    }

    private ProductPricingEntity getSampleProductPricingEntity() {
        return new ProductPricingEntity(
            1L, 1L, now, now.plusDays(1), 2, 3L, 1, new BigDecimal("99.99"), "EUR"
        );
    }

    private void assertProductPricingEquals(ProductPricing expected, ProductPricing actual) {
        assertNotNull(actual);
        assertEquals(expected.getBrandId(), actual.getBrandId());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getPriceList(), actual.getPriceList());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getPriority(), actual.getPriority());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCurrency(), actual.getCurrency());
    }

    private void assertProductPricingDTOEquals(ProductPricingDTO expected, ProductPricingDTO actual) {
        assertNotNull(actual);
        assertEquals(expected.getBrandId(), actual.getBrandId());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getPriceList(), actual.getPriceList());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getPriority(), actual.getPriority());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCurrency(), actual.getCurrency());
    }

    @Test
    void testFromDTOToDomain() {
        ProductPricingDTO dto = getSampleProductPricingDTO();
        ProductPricing domain = mapper.fromDTOToDomain(dto);
        assertProductPricingEquals(mapper.fromDTOToDomain(dto), domain);
    }

    @Test
    void testToDto() {
        ProductPricing domain = getSampleProductPricingDomain();
        ProductPricingDTO dto = mapper.toDto(domain);
        assertProductPricingDTOEquals(mapper.toDto(domain), dto);
    }

    @Test
    void testToDomainFromEntity() {
        ProductPricingEntity entity = getSampleProductPricingEntity();
        ProductPricing domain = mapper.toDomain(entity);
        assertProductPricingEquals(mapper.toDomain(entity), domain);
    }

    @Test
    void testToEntity() {
        ProductPricing domain = getSampleProductPricingDomain();
        ProductPricingEntity entity = mapper.toEntity(domain);
        assertNotNull(entity);
        assertEquals(domain.getBrandId(), entity.getBrandId());
        assertEquals(domain.getStartDate(), entity.getStartDate());
        assertEquals(domain.getEndDate(), entity.getEndDate());
        assertEquals(domain.getPriceList().intValue(), entity.getPriceList());
        assertEquals(domain.getProductId(), entity.getProductId());
        assertEquals(domain.getPriority(), entity.getPriority());
        assertEquals(domain.getPrice(), entity.getPrice());
        assertEquals(domain.getCurrency(), entity.getCurrency());
    }

    @Test
    void testNullInputs() {
        assertNull(mapper.fromDTOToDomain(null));
        assertNull(mapper.toDto(null));
        assertNull(mapper.toDomain(null));
        assertNull(mapper.toEntity(null));
    }

    @Test
    void testPartialNullFields() {
        ProductPricingDTO dto = new ProductPricingDTO();
        dto.setBrandId(1L);
        dto.setPrice(new BigDecimal("99.99"));
        ProductPricing domain = mapper.fromDTOToDomain(dto);
        assertNotNull(domain);
        assertEquals(dto.getBrandId(), domain.getBrandId());
        assertEquals(dto.getPrice(), domain.getPrice());
        assertNull(domain.getStartDate());
        assertNull(domain.getEndDate());
        assertNull(domain.getPriceList());
        assertNull(domain.getProductId());
        assertNull(domain.getPriority());
        assertNull(domain.getCurrency());
    }
}
