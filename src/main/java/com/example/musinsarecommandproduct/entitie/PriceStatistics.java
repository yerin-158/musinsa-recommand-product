package com.example.musinsarecommandproduct.entitie;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "price_statistics")
@Getter
public class PriceStatistics extends BaseEntity {

  @Id
  private Long id;

  private Long brandId;

  private Long categoryId;

  private Long highestPriceProductId;

  private Long lowestPriceProductId;

}
