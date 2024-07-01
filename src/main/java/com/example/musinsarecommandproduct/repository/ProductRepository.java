package com.example.musinsarecommandproduct.repository;

import com.example.musinsarecommandproduct.entitie.Product;
import org.springframework.data.jpa.domain.Specification;
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
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  @Query(value = "SELECT p.* " +
      "FROM products p " +
      "INNER JOIN (" +
      "    SELECT brand_id, category_id, MIN(price) as min_price " +
      "    FROM products " +
      "    WHERE status = 'EXPOSED' " +
      "    GROUP BY brand_id, category_id" +
      ") grouped_p " +
      "ON p.brand_id = grouped_p.brand_id " +
      "AND p.category_id = grouped_p.category_id " +
      "AND p.price = grouped_p.min_price " +
      "WHERE p.status = 'EXPOSED'", nativeQuery = true)
  List<Product> findProductsByMinPriceInEachBrandCategoryGroup();

  @Query(value = "SELECT p.* " +
      "FROM products p " +
      "INNER JOIN (" +
      "    SELECT brand_id, category_id, MAX(price) as min_price " +
      "    FROM products " +
      "    WHERE status = 'EXPOSED' " +
      "    GROUP BY brand_id, category_id" +
      ") grouped_p " +
      "ON p.brand_id = grouped_p.brand_id " +
      "AND p.category_id = grouped_p.category_id " +
      "AND p.price = grouped_p.min_price " +
      "WHERE p.status = 'EXPOSED'", nativeQuery = true)
  List<Product> findProductsByMaxPriceInEachBrandCategoryGroup();

}
