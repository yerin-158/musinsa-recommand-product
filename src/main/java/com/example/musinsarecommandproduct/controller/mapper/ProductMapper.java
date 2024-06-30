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
      @Mapping(source = "cheapProducts", target = "cheapProducts"),
      @Mapping(source = "expensiveProducts", target = "expensiveProducts")
  })
  ProductByCategoryResponse toProductByCategoryResponse(Category category, List<ProductResponse> cheapProducts, List<ProductResponse> expensiveProducts);


  @Mappings({
      @Mapping(source = "products", target = "products"),
      @Mapping(source = "sumPrice", target = "sumPrice")
  })
  ProductSetResponse toProductSetResponse(List<ProductResponse> products, Long sumPrice);

}
