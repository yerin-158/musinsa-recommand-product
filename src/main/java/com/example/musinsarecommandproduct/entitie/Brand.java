package com.example.musinsarecommandproduct.entitie;

import com.example.musinsarecommandproduct.enums.BrandStatus;
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
@Entity(name = "brands")
@Getter
@Setter
public class Brand extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private BrandStatus status;

}
