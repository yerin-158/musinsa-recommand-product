package com.example.musinsarecommandproduct.controller.store.mapper;

import com.example.musinsarecommandproduct.controller.store.dto.BrandSimpleResponse;
import com.example.musinsarecommandproduct.entitie.Brand;
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
public interface BrandMapper {

  BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
  BrandSimpleResponse toBrandSimpleResponse(Brand brand);

}
