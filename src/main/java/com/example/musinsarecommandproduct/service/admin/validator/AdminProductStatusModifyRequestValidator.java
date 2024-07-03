package com.example.musinsarecommandproduct.service.admin.validator;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductStatusModifyRequest;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.enums.ProductStatus;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.ProductRepository;
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
public class AdminProductStatusModifyRequestValidator {

  private final ProductRepository productRepository;

  public void validate(AdminProductStatusModifyRequest request, Long targetId) {
    if (request.status() == null) {
      throw new BadRequestException(BadRequestType.PRODUCT_STATUS_IS_REQUIRED);
    }

    Product modifyTarget = productRepository.findById(targetId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT));

    if (ProductStatus.DRAFT.equals(modifyTarget.getStatus())) {
      return;
    }

    if (ProductStatus.DELETED.equals(modifyTarget.getStatus())) {
      throw new BadRequestException(BadRequestType.DELETED_PRODUCT_CANNOT_MODIFY);
    }

    if (ProductStatus.DRAFT.equals(request.status())) {
      throw new BadRequestException(BadRequestType.PRODUCT_CANNOT_ROLLBACK_TO_DRAFT);
    }
  }

}
