package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductModifyRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductResponse;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductStatusModifyRequest;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminProductMapper;
import com.example.musinsarecommandproduct.controller.store.dto.PageResponse;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.entitie.specs.ProductSpecs;
import com.example.musinsarecommandproduct.enums.ProductStatus;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import com.example.musinsarecommandproduct.service.admin.validator.AdminProductAddRequestValidator;
import com.example.musinsarecommandproduct.service.admin.validator.AdminProductModifyRequestValidator;
import com.example.musinsarecommandproduct.service.admin.validator.AdminProductStatusModifyRequestValidator;
import com.example.musinsarecommandproduct.service.admin.validator.AdminRequiredExistValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  private final AdminRequiredExistValidator requiredExistValidator;
  private final AdminProductAddRequestValidator productAddRequestValidator;
  private final AdminProductModifyRequestValidator productModifyRequestValidator;
  private final AdminProductStatusModifyRequestValidator productStatusModifyRequestValidator;

  public AdminProductResponse findOne(Long brandId, Long productId) {
    requiredExistValidator.validate(brandId, productId, null);

    Brand brand = brandRepository.findById(brandId).get();
    Product target = productRepository.findById(productId).get();
    Category category = categoryRepository.findById(target.getCategoryId()).get();

    if (!brand.getId().equals(target.getBrandId()) || !category.getId().equals(target.getCategoryId())) {
      new BadRequestException(BadRequestType.INVALID_DATA);
    }

    return AdminProductMapper.INSTANCE.toAdminProductResponse(target, brand, category);
  }

  public PageResponse<AdminProductResponse> findAll(Long brandId, Pageable pageable) {
    requiredExistValidator.validate(brandId, null, null);

    Brand brand = brandRepository.findById(brandId).get();
    List<Category> categories = categoryRepository.findAll();
    Map<Long, Category> categoriesById = categories.stream().collect(Collectors.toMap(Category::getId, Function.identity()));

    Specification<Product> specification = Specification.where(ProductSpecs.equalsBrandId(brandId));
    Page<Product> productPage = productRepository.findAll(specification, pageable);

    if (productPage.isEmpty()) {
      return PageResponse.empty(pageable);
    }

    List<Product> products = productPage.getContent();
    List<AdminProductResponse> adminProductResponses = products.stream()
        .map(product -> AdminProductMapper.INSTANCE.toAdminProductResponse(product, brand, categoriesById.get(product.getCategoryId())))
        .collect(Collectors.toList());

    return new PageResponse<AdminProductResponse>(adminProductResponses, pageable, productPage.getTotalElements());
  }

  @Transactional
  public AdminProductResponse add(Long brandId, AdminProductAddRequest request) {
    productAddRequestValidator.validate(request);
    requiredExistValidator.validate(brandId, null, request.categoryId());

    Brand targetBrand = brandRepository.findById(brandId).get();
    Product product = AdminProductMapper.INSTANCE.toProduct(request, brandId);

    Category category = null;
    if (!product.isDraft() && product.getCategoryId() != null) {
      category = categoryRepository.findById(request.categoryId()).get();
    }

    productRepository.save(product);

    if (!product.isDraft() && targetBrand.isExposed()) {
      adminPriceStatisticsService.updatePriceStatistics(product);
    }

    return AdminProductMapper.INSTANCE.toAdminProductResponse(product, targetBrand, category);
  }

  @Transactional
  public AdminProductResponse modifyDetails(Long brandId, Long productId, AdminProductModifyRequest request) {
    requiredExistValidator.validate(brandId, productId, null);
    productModifyRequestValidator.validate(request, productId);

    Product target = productRepository.findById(productId).get();
    Brand brand = brandRepository.findById(brandId).get();
    Category category = categoryRepository.findById(target.getCategoryId()).get();
    Boolean changePrice = !target.getPrice().equals(request.price());

    Product updated = AdminProductMapper.INSTANCE.toProduct(target, request);
    productRepository.save(updated);

    if (changePrice) {
      adminPriceStatisticsService.updatePriceStatistics(updated);
    }

    return AdminProductMapper.INSTANCE.toAdminProductResponse(updated, brand, category);
  }

  @Transactional
  public AdminProductResponse modifyStatus(Long brandId, Long productId, AdminProductStatusModifyRequest request) {
    requiredExistValidator.validate(brandId, productId, null);
    productStatusModifyRequestValidator.validate(request, productId);

    Brand brand = brandRepository.findById(brandId).get();
    Product target = productRepository.findById(productId).get();
    Category category = categoryRepository.findById(target.getCategoryId()).get();

    Specification<Product> specification = Specification.where(ProductSpecs.equalsBrandId(brandId))
        .and(ProductSpecs.equalsCategoryId(target.getCategoryId()))
        .and(ProductSpecs.equalsStatus(ProductStatus.EXPOSED));
    Long count = productRepository.count(specification);
    if (brand.isExposed() && count == 1L && !request.status().isExposed()) {
      throw new BadRequestException(BadRequestType.LAST_PRODUCT_IN_CATEGORY);
    }

    Boolean changeViewable = target.getStatus().isExposed() != request.status().isExposed();

    Product updated = AdminProductMapper.INSTANCE.toProduct(target, request);
    productRepository.save(updated);

    if (changeViewable) {
      adminPriceStatisticsService.updatePriceStatistics(updated);
    }

    return AdminProductMapper.INSTANCE.toAdminProductResponse(updated, brand, category);
  }

  @Transactional
  public AdminProductResponse delete(Long brandId, Long productId) {
    requiredExistValidator.validate(brandId, productId, null);

    Brand brand = brandRepository.findById(brandId).get();
    Product target = productRepository.findById(productId).get();
    Category category = categoryRepository.findById(target.getCategoryId()).get();

    if (ProductStatus.DELETED.equals(target.getStatus())) {
      throw new BadRequestException(BadRequestType.ALREADY_DELETED_PRODUCT);
    }

    Specification<Product> specification = Specification.where(ProductSpecs.equalsBrandId(brandId))
        .and(ProductSpecs.equalsCategoryId(target.getCategoryId()))
        .and(ProductSpecs.equalsStatus(ProductStatus.EXPOSED));
    Long count = productRepository.count(specification);
    if (brand.isExposed() && count == 1L) {
      throw new BadRequestException(BadRequestType.LAST_PRODUCT_IN_CATEGORY);
    }

    target.delete();
    productRepository.save(target);
    adminPriceStatisticsService.updatePriceStatistics(target);

    return AdminProductMapper.INSTANCE.toAdminProductResponse(target, brand, category);
  }

}
