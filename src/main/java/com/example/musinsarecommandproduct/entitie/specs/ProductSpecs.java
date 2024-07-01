package com.example.musinsarecommandproduct.entitie.specs;

import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.enums.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
public class ProductSpecs {
  public static Specification<Product> equalsCategoryId(Long categoryId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryId"), categoryId);
  }

  public static Specification<Product> equalsBrandId(Long brandId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brandId"), brandId);
  }

  public static Specification<Product> equalsStatus(ProductStatus status) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
  }

}
