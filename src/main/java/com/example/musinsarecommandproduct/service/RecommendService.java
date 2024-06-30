package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.controller.dto.ProductResponse;
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
    Map<Long, Product> cheapProductsById = productFacade.getCheapProductByIdMap(priceStatistics);
    Map<Long, Brand> brandById = productFacade.getCheapBrandByIdMap(priceStatistics);

    List<Category> categories = categoryService.findAll();
    Map<Long, Category> categoriesById = categories.stream().collect(Collectors.toMap(Category::getId, Function.identity()));

    List<ProductResponse> products = cheapProductsById.values().stream()
        .map(product -> ProductMapper.INSTANCE.toProductResponse(product, brandById.get(product.getBrandId()), categoriesById.get(product.getCategoryId())))
        .collect(Collectors.toList());

    Long priceSum = cheapProductsById.values().stream()
        .mapToLong(Product::getPrice)
        .sum();

    return ProductMapper.INSTANCE.toProductSetResponse(products, priceSum);
  }

}
