package com.example.musinsarecommandproduct.entitie.specs;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public class PriceStatisticsSpecs {

  public static Specification<PriceStatistics> equalsCategoryId(Long categoryId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryId"), categoryId);
  }

}
