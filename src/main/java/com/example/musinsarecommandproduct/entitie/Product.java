package com.example.musinsarecommandproduct.entitie;

import com.example.musinsarecommandproduct.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "products")
@Getter
@Setter
public class Product extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long brandId;

  private Long categoryId;
  private String name;
  private Integer price;

  @Enumerated(EnumType.STRING)
  private ProductStatus status;

}
