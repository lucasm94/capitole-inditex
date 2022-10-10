package com.inditex.prices.repository;

import com.inditex.prices.entity.Prices;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Prices, Long> {
  @Query(value = "Select p from Prices p where p.brand.id = :brandId and p.product.id = :productId and :startDate between p.startDate and p.endDate")
  List<Prices> findAllByStartDate(@Param("brandId") Long brandId, @Param("productId")
  Long productId, @Param("startDate") LocalDateTime startDate);

  List<Prices> findAllByBrandIdAndProductId(@Param("brandId") Long brandId, @Param("productId") Long productId);
}
