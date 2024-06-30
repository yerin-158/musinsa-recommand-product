package com.example.musinsarecommandproduct.entitie;

import com.example.musinsarecommandproduct.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "products")
@Getter
public class Product extends BaseEntity{

  @Id
  private Long id;

  private Long brandId;

  private Long categoryId;
  private String name;
  private Integer price;

  @Enumerated(EnumType.STRING)
  private ProductStatus status;

}
