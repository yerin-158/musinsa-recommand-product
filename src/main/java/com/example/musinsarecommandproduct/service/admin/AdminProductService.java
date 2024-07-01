package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductResponse;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminProductMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
    adminPriceStatisticsService.update(product);

    return AdminProductMapper.INSTANCE.toAdminProductResponse(product, targetBrand, category);
  }

}
