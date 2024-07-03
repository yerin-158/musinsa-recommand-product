package com.example.musinsarecommandproduct.controller.admin.mapper;

import com.example.musinsarecommandproduct.controller.admin.dto.*;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
@Mapper
public interface AdminProductMapper {

  AdminProductMapper INSTANCE = Mappers.getMapper(AdminProductMapper.class);

  @Mapping(target = "id", ignore = true)
  Product toProduct(AdminProductAddRequest request, Long brandId);

  @Mapping(target = "status", source = "request.status")
  Product toProduct(@MappingTarget Product product, AdminProductStatusModifyRequest request);

  AdminSimpleProductResponse toAdminSimpleProductResponse(Product product);

  @Mappings({
      @Mapping(source = "product", target = "product"),
      @Mapping(source = "brand", target = "brand"),
      @Mapping(source = "category", target = "category")
  })
  AdminProductResponse toAdminProductResponse(Product product, Brand brand, Category category);

  @Mappings({
      @Mapping(target = "name", source = "request.name"),
      @Mapping(target = "price", source = "request.price")
  })
  Product toProduct(@MappingTarget Product product, AdminProductModifyRequest request);

}
