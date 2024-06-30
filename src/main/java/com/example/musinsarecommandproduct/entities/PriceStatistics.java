package com.example.musinsarecommandproduct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "price_statistics")
public class PriceStatistics extends BaseEntity{

  @Id
  private Long id;

  private Long brandId;

  private Long categoryId;

  private Long highestPriceProductId;

  private Long lowestPriceProductId;

}
