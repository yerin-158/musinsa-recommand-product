package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.entitie.specs.PriceStatisticsSpecs;
import com.example.musinsarecommandproduct.entitie.specs.ProductSpecs;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import com.example.musinsarecommandproduct.repository.PriceStatisticsRepository;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

  @Transactional
  public void updatePriceStatistics(Product product) {
    Specification<PriceStatistics> specification = Specification
        .where(PriceStatisticsSpecs.equalsBrandId(product.getBrandId()))
        .and(PriceStatisticsSpecs.equalsCategoryId(product.getCategoryId()));
    Optional<PriceStatistics> nowPriceStatisticsOptional = priceStatisticsRepository.findOne(specification);

    if (nowPriceStatisticsOptional.isEmpty()) {
      PriceStatistics newPriceStatistics = PriceStatistics.createFirst(product);
      priceStatisticsRepository.save(newPriceStatistics);
      return;
    }

    PriceStatistics nowPriceStatistics = nowPriceStatisticsOptional.get();
    // 처음 insert 할 때 반드시 채워서 넣기때문에 null인 것 있으면 버그임
    if (nowPriceStatistics.getLowestPrice() == null || nowPriceStatistics.getHighestPrice() == null) {
      throw new BadRequestException(BadRequestType.INVALID_PRICE_STATISTICS);
    }

    if (product.isExposed() && product.isPriceLowerThan(nowPriceStatistics.getLowestPrice())) {
      nowPriceStatistics.updateLowest(product);
    } else if (product.isPriceLowestProduct(nowPriceStatistics.getLowestPriceProductId()) && (!product.isExposed() || product.isPriceHigherThan(nowPriceStatistics.getLowestPrice()))) {
      updateLowestPriceProductStatistics(nowPriceStatistics);
    }

    if (product.isExposed() && product.isPriceHigherThan(nowPriceStatistics.getHighestPrice())) {
      nowPriceStatistics.updateHighest(product);
    } else if (product.isPriceHighestProduct(nowPriceStatistics.getHighestPriceProductId()) && (!product.isExposed() || product.isPriceLowerThan(nowPriceStatistics.getHighestPrice()))) {
      updateHighestPriceProductStatistics(nowPriceStatistics);
    }

    priceStatisticsRepository.save(nowPriceStatistics);
  }

  @Transactional
  public void addByBrand(Brand brand) {
    // 브랜드 ID로 모든 상품 가져오기
    Specification<Product> specification = Specification.where(ProductSpecs.equalsBrandId(brand.getId()));
    List<Product> products = productRepository.findAll(specification);

    // 카테고리별로 그룹화하여 가장 비싼 상품과 가장 저렴한 상품 찾기
    Map<Long, List<Product>> productsByCategory = products.stream().collect(Collectors.groupingBy(Product::getCategoryId));

    productsByCategory.forEach((categoryId, productList) -> {
      Optional<Product> highestPriceProduct = productList.stream().max(Comparator.comparing(Product::getPrice));
      Optional<Product> lowestPriceProduct = productList.stream().min(Comparator.comparing(Product::getPrice));

      if (highestPriceProduct.isPresent() && lowestPriceProduct.isPresent()) {
        PriceStatistics priceStatistics = PriceStatistics.createFirst(highestPriceProduct.get(), lowestPriceProduct.get());
        priceStatisticsRepository.save(priceStatistics);
      } else {
        throw new BadRequestException(BadRequestType.INVALID_PRICE_STATISTICS);
      }
    });
  }

  public void removeAllByBrand(Brand brand) {
    Specification<PriceStatistics> specification = Specification.where(PriceStatisticsSpecs.equalsBrandId(brand.getId()));
    priceStatisticsRepository.delete(specification);
  }

  private void updateLowestPriceProductStatistics(PriceStatistics nowPriceStatistics) {
    List<Product> lowestProducts = productRepository.findLowestPriceProductByBrandAndCategory(nowPriceStatistics.getBrandId(), nowPriceStatistics.getCategoryId());
    Product newLowestProduct = getNewProductWithMaxId(lowestProducts);
    nowPriceStatistics.updateLowest(newLowestProduct);
  }

  private void updateHighestPriceProductStatistics(PriceStatistics nowPriceStatistics) {
    List<Product> highestProducts = productRepository.findHighestProductByBrandAndCategory(nowPriceStatistics.getBrandId(), nowPriceStatistics.getCategoryId());
    Product newHighestProduct = getNewProductWithMaxId(highestProducts);
    nowPriceStatistics.updateHighest(newHighestProduct);
  }

  private Product getNewProductWithMaxId(List<Product> products) {
    if (products.size() == 1) {
      return products.get(0);
    }

    return products.stream()
        .sorted((p1, p2) -> Long.compare(p2.getId(), p1.getId())) // ID 내림차순 정렬
        .collect(Collectors.toList())
        .get(0);
  }


}
