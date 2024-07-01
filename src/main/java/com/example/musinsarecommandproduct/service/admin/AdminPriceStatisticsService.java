package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.entitie.specs.PriceStatisticsSpecs;
import com.example.musinsarecommandproduct.repository.PriceStatisticsRepository;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
  private final ProductRepository productRepository;

  public void updateDueToExposedChange(Product product) {
    Specification<PriceStatistics> specification = Specification
        .where(PriceStatisticsSpecs.equalsBrandId(product.getBrandId()))
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

      if (product.isPriceLowerThan(nowPriceStatistics.getLowestPrice())) {
        nowPriceStatistics.updateLowest(product);
      }

      if (product.isPriceHigherThan(nowPriceStatistics.getHighestPrice())) {
        nowPriceStatistics.updateHighest(product);
      }

      priceStatisticsRepository.save(nowPriceStatistics);
    }
  }

  /**
   * 비노출로 변경된 경우
   * @param updatedProduct
   */
  public void updateDueToNotExposedChange(Product updatedProduct) {
    Specification<PriceStatistics> specification = Specification
        .where(PriceStatisticsSpecs.equalsBrandId(updatedProduct.getBrandId()))
        .and(PriceStatisticsSpecs.equalsCategoryId(updatedProduct.getCategoryId()));
    PriceStatistics nowPriceStatistics = priceStatisticsRepository.findOne(specification).orElseThrow(() -> new RuntimeException());

    if (updatedProduct.isPriceLowerThan(nowPriceStatistics.getLowestPriceProductId())) {
      updateLowestPriceProduct(nowPriceStatistics);
    }

    if (updatedProduct.isPriceHigherThan(nowPriceStatistics.getHighestPriceProductId())) {
      updateHighestPriceProduct(nowPriceStatistics);
    }

    priceStatisticsRepository.save(nowPriceStatistics);
  }

  private void updateLowestPriceProduct(PriceStatistics nowPriceStatistics) {
    List<Product> lowestProducts = productRepository.findProductsByMinPriceInEachBrandCategoryGroup();
    Product newLowestProduct = getNewProductWithMaxId(lowestProducts);
    nowPriceStatistics.updateLowest(newLowestProduct);
  }

  private void updateHighestPriceProduct(PriceStatistics nowPriceStatistics) {
    List<Product> highestProducts = productRepository.findProductsByMaxPriceInEachBrandCategoryGroup();
    Product newHighestProduct = getNewProductWithMaxId(highestProducts);
    nowPriceStatistics.updateHighest(newHighestProduct);
  }

  private Product getNewProductWithMaxId(List<Product> products) {
    if (products.size() == 1) {
      return products.get(0);
    } else {
      return products.stream()
          .sorted((p1, p2) -> Long.compare(p2.getId(), p1.getId())) // ID 내림차순 정렬
          .collect(Collectors.toList())
          .get(0);
    }
  }
}
