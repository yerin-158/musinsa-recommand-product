package com.example.musinsarecommandproduct.controller.admin.mapper;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandResponse;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandStatusModifyRequest;
import com.example.musinsarecommandproduct.entitie.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
@Mapper
public interface AdminBrandMapper {

  AdminBrandMapper INSTANCE = Mappers.getMapper(AdminBrandMapper.class);

  @Mapping(target = "status", expression = "java(com.example.musinsarecommandproduct.enums.BrandStatus.NOT_EXPOSED)")
  @Mapping(target = "id", ignore = true)
  Brand toBrand(AdminBrandAddRequest request);

  AdminBrandResponse toAdminBrandResponse(Brand brand);

  @Mapping(target = "status", source = "request.status")
  Brand toBrand(@MappingTarget Brand brand, AdminBrandStatusModifyRequest request);
}
