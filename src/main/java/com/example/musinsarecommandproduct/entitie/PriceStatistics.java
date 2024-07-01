package com.example.musinsarecommandproduct.entitie;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "price_statistics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceStatistics extends BaseEntity {

  @Id
  private Long id;

  private Long brandId;

  private Long categoryId;

  private Long highestPriceProductId;

  private Long lowestPriceProductId;

  private Integer highestPrice;

  private Integer lowestPrice;

  public static PriceStatistics createFirst(Product product) {
    PriceStatistics priceStatistics = new PriceStatistics();
    priceStatistics.brandId = product.getBrandId();
    priceStatistics.categoryId = product.getCategoryId();
    priceStatistics.highestPriceProductId = product.getId();
    priceStatistics.lowestPriceProductId = product.getId();
    priceStatistics.highestPrice = product.getPrice();
    priceStatistics.lowestPrice = product.getPrice();
    return priceStatistics;
  }

  public void updateHighest(Product product) {
    this.highestPriceProductId = product.getId();
    this.highestPrice = product.getPrice();
  }

  public void updateLowest(Product product) {
    this.lowestPriceProductId = product.getId();
    this.lowestPrice = product.getPrice();
  }

}
