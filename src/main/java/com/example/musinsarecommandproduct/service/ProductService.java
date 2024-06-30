package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.controller.dto.ProductByCategoryResponse;
import com.example.musinsarecommandproduct.controller.mapper.ProductMapper;
import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.enums.PriceType;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final BrandService brandService;
  private final PriceStatisticsService priceStatisticsService;

  public ProductByCategoryResponse getProductByCategory(Long categoryId, List<PriceType> priceType, Integer size) {
    List<PriceStatistics> cheapPriceStats = new ArrayList<>();
    List<PriceStatistics> expensivePriceStats = new ArrayList<>();

    if (priceType.contains(PriceType.CHEAP)) {
      cheapPriceStats = priceStatisticsService.findCheapPriceProductByCategoryId(categoryId, size);
    }

    if (priceType.contains(PriceType.EXPENSIVE)) {
      expensivePriceStats = priceStatisticsService.findExpensiveProductByCategoryId(categoryId, size);
    }

    Set<Long> productIds = new HashSet<>();
    productIds.addAll(cheapPriceStats.stream().map(PriceStatistics::getLowestPriceProductId).collect(Collectors.toSet()));
    productIds.addAll(expensivePriceStats.stream().map(PriceStatistics::getHighestPriceProductId).collect(Collectors.toSet()));

    List<Product> products = productRepository.findAllById(productIds);



    ProductMapper.INSTANCE.toProductByCategoryResponse(null, cheapPriceStats, expensivePriceStats);




    return null;
  }


}
