package com.example.musinsarecommandproduct.service.admin.validator;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.enums.ProductStatus;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import org.springframework.stereotype.Component;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Component
public class AdminProductAddRequestValidator {

  public void validate(AdminProductAddRequest request) {
    if (request.status() == null) {
      throw new BadRequestException(BadRequestType.PRODUCT_STATUS_IS_REQUIRED);
    }

    if (ProductStatus.DRAFT.equals(request.status())) {
      return;
    }

    if (request.name() == null || request.name().isBlank()) {
      throw new BadRequestException(BadRequestType.PRODUCT_NAME_IS_REQUIRED);
    }

    if (request.categoryId() == null || request.categoryId() <= 0L) {
      throw new BadRequestException(BadRequestType.PRODUCT_CATEGORY_ID_IS_REQUIRED);
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
