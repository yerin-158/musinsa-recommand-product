package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.controller.dto.ProductSetResponse;
import com.example.musinsarecommandproduct.controller.mapper.ProductMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
public class RecommendService {

  private final PriceStatisticsService priceStatisticsService;
  private final CategoryService categoryService;
  private final ProductFacade productFacade;

  public ProductSetResponse getProductSetByBrand(Long brandId) {
    List<PriceStatistics> priceStatistics = priceStatisticsService.retrieve(null, brandId);
    return mapPriceStatisticsToProductSetResponse(priceStatistics);
  }

  public ProductSetResponse getLowestPriceProductSet() {
    List<PriceStatistics> lowestPriceStats = priceStatisticsService.getLowestPriceProductsByCategory();
    return mapPriceStatisticsToProductSetResponse(lowestPriceStats);
  }

  private ProductSetResponse mapPriceStatisticsToProductSetResponse(List<PriceStatistics> priceStatistics) {
    Map<Long, Product> lowestPriceProductsById = productFacade.getLowestPriceProductByIdMap(priceStatistics);
    Map<Long, Brand> brandById = productFacade.getLowestPriceBrandByIdMap(priceStatistics);

    List<Category> categories = categoryService.findAll();
    Map<Long, Category> categoriesById = categories.stream().collect(Collectors.toMap(Category::getId, Function.identity()));

    return ProductMapper.INSTANCE.toProductSetResponse(lowestPriceProductsById.values().stream().toList(), categoriesById, brandById);
  }

}
