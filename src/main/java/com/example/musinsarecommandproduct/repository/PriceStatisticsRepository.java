package com.example.musinsarecommandproduct.repository;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public interface PriceStatisticsRepository extends JpaRepository<PriceStatistics, Long>, JpaSpecificationExecutor<PriceStatistics> {
  @Query(value =
          "SELECT ps.* " +
          "FROM price_statistics ps " +
          "WHERE (ps.category_id, ps.lowest_price) " +
            "IN (SELECT category_id, MIN(lowest_price) " +
              "FROM price_statistics GROUP BY category_id)", nativeQuery = true)
  List<PriceStatistics> findLowestPriceProductsByCategory();
}
