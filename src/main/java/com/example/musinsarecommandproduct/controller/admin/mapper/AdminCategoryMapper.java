package com.example.musinsarecommandproduct.controller.admin.mapper;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminCategoryResponse;
import com.example.musinsarecommandproduct.entitie.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
@Mapper
public interface AdminCategoryMapper {

  AdminCategoryMapper INSTANCE = Mappers.getMapper(AdminCategoryMapper.class);

  AdminCategoryResponse toAdminCategoryResponse(Category category);

}
