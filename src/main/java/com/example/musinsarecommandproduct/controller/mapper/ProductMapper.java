package com.example.musinsarecommandproduct.controller.mapper;

import com.example.musinsarecommandproduct.controller.dto.ProductByCategoryResponse;
import com.example.musinsarecommandproduct.controller.dto.ProductResponse;
import com.example.musinsarecommandproduct.controller.dto.ProductSetResponse;
import com.example.musinsarecommandproduct.controller.dto.ProductSimpleResponse;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductSimpleResponse toProductSimpleResponse(Product product);

  @Mappings({
      @Mapping(source = "product", target = "product"),
      @Mapping(source = "brand", target = "brand"),
      @Mapping(source = "category", target = "category")
  })
  ProductResponse toProductResponse(Product product, Brand brand, Category category);

  @Mappings({
      @Mapping(source = "category", target = "category"),
      @Mapping(source = "lowestPriceProducts", target = "lowestPriceProducts"),
      @Mapping(source = "highestPriceProducts", target = "highestPriceProducts")
  })
  ProductByCategoryResponse toProductByCategoryResponse(Category category, List<ProductResponse> lowestPriceProducts, List<ProductResponse> highestPriceProducts);


  @Mappings({
      @Mapping(source = "products", target = "products"),
      @Mapping(source = "sumPrice", target = "sumPrice")
  })
  ProductSetResponse toProductSetResponse(List<ProductResponse> products, Long sumPrice);

  default ProductByCategoryResponse toProductByCategoryResponse(Category category, List<Product> lowestPriceProducts, List<Product> highestPriceProducts, Map<Long, Brand> brandById) {
    List<ProductResponse> lowestPriceProductResponses = toProductResponseList(lowestPriceProducts, Map.of(category.getId(), category), brandById);
    List<ProductResponse> highestPriceProductResponses = toProductResponseList(highestPriceProducts, Map.of(category.getId(), category), brandById);

    return toProductByCategoryResponse(category, lowestPriceProductResponses, highestPriceProductResponses);
  }

  default ProductSetResponse toProductSetResponse(List<Product> products, Map<Long, Category> categoriesById, Map<Long, Brand> brandById) {
    List<ProductResponse> productResponses = toProductResponseList(products, categoriesById, brandById);
    Long sumPrice = products.stream().mapToLong(Product::getPrice).sum();
    return toProductSetResponse(productResponses, sumPrice);
  }

  default List<ProductResponse> toProductResponseList(List<Product> products, Map<Long, Category> categoryById, Map<Long, Brand> brandById) {
    return products.stream()
        .map(product -> toProductResponse(product, brandById.get(product.getBrandId()), categoryById.get(product.getCategoryId())))
        .collect(Collectors.toList());
  }
}
