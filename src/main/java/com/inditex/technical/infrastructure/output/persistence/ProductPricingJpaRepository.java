package com.inditex.technical.infrastructure.output.persistence;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPricingJpaRepository extends JpaRepository<ProductPricingEntity, Long> {

    @Query("SELECT p FROM ProductPricingEntity p WHERE p.startDate <= :startDate AND p.endDate >= :startDate AND p.productId = :productId AND p.brandId = :brandId ORDER BY p.priority DESC LIMIT 1")
    ProductPricingEntity findPrice(
        @Param("startDate") LocalDateTime startDate,
        @Param("productId") Long productId,
        @Param("brandId") Long brandId
    );
}
