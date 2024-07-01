package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.entitie.specs.PriceStatisticsSpecs;
import com.example.musinsarecommandproduct.repository.PriceStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class AdminPriceStatisticsService {

  private final PriceStatisticsRepository priceStatisticsRepository;

  public void update(Product product) {
    Specification<PriceStatistics> specification = Specification.where(PriceStatisticsSpecs.equalsBrandId(product.getBrandId()))
        .and(PriceStatisticsSpecs.equalsCategoryId(product.getCategoryId()));
    Optional<PriceStatistics> nowPriceStatisticsOptional = priceStatisticsRepository.findOne(specification);
    if (nowPriceStatisticsOptional.isEmpty()) {
      PriceStatistics newPriceStatistics = PriceStatistics.createFirst(product);
      priceStatisticsRepository.save(newPriceStatistics);
    } else {
      PriceStatistics nowPriceStatistics = nowPriceStatisticsOptional.get();

      // 처음 insert 할 때 반드시 채워서 넣기때문에 null인 것 있으면 버그임
      if (nowPriceStatistics.getLowestPrice() == null || nowPriceStatistics.getHighestPrice() == null) {
        throw new RuntimeException();
      }

      if (nowPriceStatistics.getLowestPrice() > product.getPrice()) {
        nowPriceStatistics.updateLowest(product);
      }

      if (nowPriceStatistics.getHighestPrice() < product.getPrice()) {
        nowPriceStatistics.updateHighest(product);
      }

      priceStatisticsRepository.save(nowPriceStatistics);
    }
  }

}
