package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.controller.dto.ProductByCategoryResponse;
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
public class ProductService {

  private final ProductFacade productFacade;

  public ProductByCategoryResponse getProductByCategory(Long categoryId, List<PriceType> priceTypes, Integer size) {
    List<PriceStatistics> cheapPriceStats = productFacade.getPriceStatistics(categoryId, priceTypes, PriceType.CHEAP, size);
    List<PriceStatistics> expensivePriceStats = productFacade.getPriceStatistics(categoryId, priceTypes, PriceType.EXPENSIVE, size);

    Map<Long, Product> productById = productFacade.getProductByIdMap(cheapPriceStats, expensivePriceStats);
    Map<Long, Brand> brandById = productFacade.getBrandByIdMap(cheapPriceStats, expensivePriceStats);

    List<Product> cheapProducts = productFacade.findProducts(cheapPriceStats, productById, PriceStatistics::getLowestPriceProductId);
    List<Product> expensiveProducts = productFacade.findProducts(expensivePriceStats, productById, PriceStatistics::getHighestPriceProductId);

    Category category = productFacade.getCategory(categoryId);
    List<ProductResponse> cheapProductResponses = productFacade.mapProductsToProductResponses(cheapProducts, category, brandById);
    List<ProductResponse> expensiveProductResponses = productFacade.mapProductsToProductResponses(expensiveProducts, category, brandById);

    return ProductMapper.INSTANCE.toProductByCategoryResponse(category, cheapProductResponses, expensiveProductResponses);
  }
}
