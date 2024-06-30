package com.example.musinsarecommandproduct.controller.mapper;

import com.example.musinsarecommandproduct.controller.dto.CategorySimpleResponse;
import com.example.musinsarecommandproduct.entitie.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Mapper
public interface CategoryMapper {
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
  CategorySimpleResponse toCategorySimpleResponse(Category category);

}
