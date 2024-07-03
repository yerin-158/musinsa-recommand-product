package com.example.musinsarecommandproduct.service.admin.validator;

import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
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
public class AdminRequiredExistValidator {

  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public void validate(Long brandId, Long productId, Long categoryId) {
    if (brandId != null && !brandRepository.existsById(brandId)) {
      throw new BadRequestException(BadRequestType.NOT_FOUND_BRAND);
    }

    if (productId != null && !productRepository.existsById(productId)) {
      throw new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT);
    }

    if (categoryId != null && !categoryRepository.existsById(categoryId)) {
      throw new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY);
    }
  }
}
