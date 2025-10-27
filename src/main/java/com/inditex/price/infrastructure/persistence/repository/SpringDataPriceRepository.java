package com.inditex.price.infrastructure.persistence.repository;

import com.inditex.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.price.infrastructure.persistence.entity.PriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, PriceId> {

    
    @Query("""
            SELECT p FROM PriceEntity p
            WHERE (:brandId IS NULL OR p.id.brandId = :brandId)
              AND (:productId IS NULL OR p.id.productId = :productId)
              AND (:applicationDate IS NULL OR 
                  (p.startDate <= :applicationDate AND p.endDate >= :applicationDate))
        """)
	List<PriceEntity> findApplicablePrices(Long productId,
		                                       Long brandId,
		                                       LocalDateTime applicationDate);
}
