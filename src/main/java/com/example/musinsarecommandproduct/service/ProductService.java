package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.controller.dto.ProductByCategoryResponse;
import com.example.musinsarecommandproduct.controller.mapper.ProductMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.enums.PriceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
    List<PriceStatistics> lowestPriceStats = productFacade.getPriceStatistics(categoryId, priceTypes, PriceType.LOW, size);
    List<PriceStatistics> highestPriceStats = productFacade.getPriceStatistics(categoryId, priceTypes, PriceType.HIGH, size);

    Map<Long, Product> productById = productFacade.getProductByIdMap(lowestPriceStats, highestPriceStats);
    Map<Long, Brand> brandById = productFacade.getBrandByIdMap(lowestPriceStats, highestPriceStats);

    List<Product> lowestPriceProducts = productFacade.findProducts(lowestPriceStats, productById, PriceStatistics::getLowestPriceProductId);
    List<Product> highestPriceProducts = productFacade.findProducts(highestPriceStats, productById, PriceStatistics::getHighestPriceProductId);

    Category category = productFacade.getCategory(categoryId);
    return ProductMapper.INSTANCE.toProductByCategoryResponse(category, lowestPriceProducts, highestPriceProducts, brandById);
  }
}
