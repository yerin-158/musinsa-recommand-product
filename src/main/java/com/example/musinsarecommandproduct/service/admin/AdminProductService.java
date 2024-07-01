package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductResponse;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductStatusModifyRequest;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminProductMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.entitie.specs.ProductSpecs;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class AdminProductService {

  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final AdminPriceStatisticsService adminPriceStatisticsService;

  @Transactional
  public AdminProductResponse add(Long brandId, AdminProductAddRequest request) {
    //TODO validation 추가
    Brand targetBrand = brandRepository.findById(brandId).orElseThrow(() -> new RuntimeException());
    Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new RuntimeException());

    Product product = AdminProductMapper.INSTANCE.toProduct(request, brandId);
    productRepository.save(product);
    adminPriceStatisticsService.updateDueToExposedChange(product);

    return AdminProductMapper.INSTANCE.toAdminProductResponse(product, targetBrand, category);
  }

  @Transactional
  public AdminProductResponse modifyStatus(Long brandId, Long productId, AdminProductStatusModifyRequest request) {
    Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new RuntimeException());
    Product target = productRepository.findById(productId).orElseThrow(() -> new RuntimeException());

    Specification<Product> specification = Specification.where(ProductSpecs.equalsCategoryId(target.getCategoryId()));
    Long count = productRepository.count(specification);
    if (brand.getStatus().isExposed() && count == 1L && !request.status().isExposed()) {
      // 해당 상품이 해당 카테고리의 유일한 상품일 경우 안 보이게 변경 못함
      throw new RuntimeException();
    }

    Boolean changeViewable = target.getStatus().isExposed() != request.status().isExposed();

    Product updated = AdminProductMapper.INSTANCE.toProduct(target, request);
    productRepository.save(updated);

    if (changeViewable) {
      if (request.status().isExposed()) {
        // 노출로 변경된 경우
        adminPriceStatisticsService.updateDueToExposedChange(updated);
      } else {
        // 비노출로 변경된 경우
        adminPriceStatisticsService.updateDueToNotExposedChange(updated);
      }
    }

    Category category = categoryRepository.findById(target.getCategoryId()).orElseThrow(() -> new RuntimeException());
    return AdminProductMapper.INSTANCE.toAdminProductResponse(updated, brand, category);
  }

}
