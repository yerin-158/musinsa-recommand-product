package com.example.musinsarecommandproduct.entitie.specs;

import com.example.musinsarecommandproduct.entitie.Brand;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
public class BrandSpecs {

  public static Specification<Brand> equalsName(String name) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
  }
}
