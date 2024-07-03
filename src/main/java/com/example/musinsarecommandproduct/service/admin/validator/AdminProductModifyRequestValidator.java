package com.example.musinsarecommandproduct.service.admin.validator;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductModifyRequest;
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
public class AdminProductModifyRequestValidator {

  private final ProductRepository productRepository;

  public void validate(AdminProductModifyRequest request, Long targetId) {
    Product modifyTarget = productRepository.findById(targetId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT));

    if (ProductStatus.DRAFT.equals(modifyTarget.getStatus())) {
      return;
    }

    if (ProductStatus.DELETED.equals(modifyTarget.getStatus())) {
      throw new BadRequestException(BadRequestType.DELETED_PRODUCT_CANNOT_MODIFY);
    }

    if (request.name() == null || request.name().isBlank()) {
      throw new BadRequestException(BadRequestType.PRODUCT_NAME_IS_REQUIRED);
    }

    if (request.price() == null) {
      throw new BadRequestException(BadRequestType.PRODUCT_PRICE_IS_REQUIRED);
    }

    if (request.price() == 0) {
      throw new BadRequestException(BadRequestType.PRODUCT_PRICE_CANNOT_BE_ZERO);
    }

    if (request.price() < 0) {
      throw new BadRequestException(BadRequestType.PRODUCT_PRICE_CANNOT_BE_NEGATIVE);
    }
  }

}
