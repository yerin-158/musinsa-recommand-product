package com.example.musinsarecommandproduct.service.admin.validator;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandStatusModifyRequest;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.enums.BrandStatus;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Component
@RequiredArgsConstructor
public class AdminBrandStatusModifyRequestValidator {

  private final BrandRepository brandRepository;

  public void validate(AdminBrandStatusModifyRequest request, Long targetId) {
    if (request.status() == null) {
      throw new BadRequestException(BadRequestType.BRAND_STATUS_IS_REQUIRED);
    }

    Brand modifyTarget = brandRepository.findById(targetId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));

    if (BrandStatus.DELETED.equals(modifyTarget.getStatus())) {
      throw new BadRequestException(BadRequestType.DELETED_BRAND_CANNOT_MODIFY);
    }
  }

}
