package com.example.musinsarecommandproduct.service.admin.validator;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandAddRequest;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.specs.BrandSpecs;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Component
@RequiredArgsConstructor
public class AdminBrandAddRequestValidator {

  private final BrandRepository brandRepository;

  public void validate(AdminBrandAddRequest request) {
    // 필수 필드 검증
    if (request.name() == null || request.name().isBlank()) {
      throw new BadRequestException(BadRequestType.BRAND_NAME_IS_REQUIRED);
    }

    // 중복 이름 검증
    Specification<Brand> specification = Specification.where(BrandSpecs.equalsName(request.name()));
    Optional<Brand> sameNameBrand = brandRepository.findOne(specification);

    if (sameNameBrand.isPresent()) {
      throw new BadRequestException(BadRequestType.DUPLICATE_BRAND_NAME);
    }
  }

}
