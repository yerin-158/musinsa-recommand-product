package com.example.musinsarecommandproduct.entities;

import com.example.musinsarecommandproduct.enums.BrandStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "brands")
public class Brand extends BaseEntity {

  @Id
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private BrandStatus status;

}
