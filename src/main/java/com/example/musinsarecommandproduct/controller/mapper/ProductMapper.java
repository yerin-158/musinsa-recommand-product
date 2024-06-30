package com.example.musinsarecommandproduct.controller.mapper;

import com.example.musinsarecommandproduct.controller.dto.ProductResponse;
import com.example.musinsarecommandproduct.controller.dto.ProductSimpleResponse;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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

}
