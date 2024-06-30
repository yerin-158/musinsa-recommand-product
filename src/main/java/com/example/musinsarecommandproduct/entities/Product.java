package com.example.musinsarecommandproduct.entities;

import com.example.musinsarecommandproduct.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "products")
public class Product extends BaseEntity{

  @Id
  private Long id;

  private Long brandId;

  private Long categoryId;
  private String name;
  private Integer price;
  private ProductStatus status;

}
