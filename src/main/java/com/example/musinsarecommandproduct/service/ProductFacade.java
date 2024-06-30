package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.controller.dto.ProductResponse;
import com.example.musinsarecommandproduct.controller.mapper.ProductMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.enums.PriceType;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class ProductFacade {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final BrandService brandService;
  private final PriceStatisticsService priceStatisticsService;

  public List<PriceStatistics> getPriceStatistics(Long categoryId, List<PriceType> priceTypes, PriceType priceType, Integer size) {
    return priceTypes.contains(priceType) ? priceStatisticsService.find(categoryId, priceType, size) : Collections.emptyList();
  }

  public Map<Long, Product> getProductByIdMap(List<PriceStatistics> cheapPriceStats, List<PriceStatistics> expensivePriceStats) {
    Set<Long> productIds = Stream.concat(
        cheapPriceStats.stream().map(PriceStatistics::getLowestPriceProductId),
        expensivePriceStats.stream().map(PriceStatistics::getHighestPriceProductId)
    ).collect(Collectors.toSet());
    List<Product> products = productRepository.findAllById(productIds);
    return products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
  }

  public List<Product> findProducts(List<PriceStatistics> stats, Map<Long, Product> productById, Function<PriceStatistics, Long> idExtractor) {
    return stats.stream()
        .map(stat -> productById.get(idExtractor.apply(stat)))
        .collect(Collectors.toList());
  }

  public Map<Long, Brand> getBrandByIdMap(List<PriceStatistics> cheapPriceStats, List<PriceStatistics> expensivePriceStats) {
    Set<Long> brandIds = Stream.concat(
        cheapPriceStats.stream().map(PriceStatistics::getBrandId),
        expensivePriceStats.stream().map(PriceStatistics::getBrandId)
    ).collect(Collectors.toSet());

    List<Brand> brands = brandService.findAllById(brandIds);
    return brands.stream().collect(Collectors.toMap(Brand::getId, Function.identity()));
  }

  public Category getCategory(Long categoryId) {
    return categoryService.findById(categoryId);
  }

  public List<ProductResponse> mapProductsToProductResponses(List<Product> products, Category category, Map<Long, Brand> brandById) {
    return products.stream()
        .map(product -> ProductMapper.INSTANCE.toProductResponse(product, brandById.get(product.getBrandId()), category))
        .collect(Collectors.toList());
  }

}
