package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductModifyRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductResponse;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductStatusModifyRequest;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminProductMapper;
import com.example.musinsarecommandproduct.controller.dto.PageResponse;
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

  public AdminProductResponse findOne(Long brandId, Long productId) {
    Brand brand = brandRepository.findById(brandId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
    Product target = productRepository.findById(productId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT));
    Category category = categoryRepository.findById(target.getCategoryId())
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY));

    return AdminProductMapper.INSTANCE.toAdminProductResponse(target, brand, category);
  }

  public PageResponse<AdminProductResponse> findAll(Long brandId, Pageable pageable) {
    Brand brand = brandRepository.findById(brandId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
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
    //TODO validation 추가
    Brand targetBrand = brandRepository.findById(brandId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY));

    Product product = AdminProductMapper.INSTANCE.toProduct(request, brandId);
    productRepository.save(product);

    if (!product.isDraft()) {
      adminPriceStatisticsService.updatePriceStatistics(product);
    }

    return AdminProductMapper.INSTANCE.toAdminProductResponse(product, targetBrand, category);
  }

  @Transactional
  public AdminProductResponse modifyStatus(Long brandId, Long productId, AdminProductStatusModifyRequest request) {
    Brand brand = brandRepository.findById(brandId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
    Product target = productRepository.findById(productId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT));
    Category category = categoryRepository.findById(target.getCategoryId())
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY));

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
  public AdminProductResponse modifyDetails(Long brandId, Long productId, AdminProductModifyRequest request) {
    Brand brand = brandRepository.findById(brandId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
    Product target = productRepository.findById(productId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT));
    Category category = categoryRepository.findById(target.getCategoryId())
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY));

    Boolean changePrice = !target.getPrice().equals(request.price());

    Product updated = AdminProductMapper.INSTANCE.toProduct(target, request);
    productRepository.save(updated);

    if (changePrice) {
      adminPriceStatisticsService.updatePriceStatistics(updated);
    }

    return AdminProductMapper.INSTANCE.toAdminProductResponse(updated, brand, category);
  }

  @Transactional
  public AdminProductResponse delete(Long brandId, Long productId) {
    Brand brand = brandRepository.findById(brandId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
    Product target = productRepository.findById(productId)
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_PRODUCT));
    Category category = categoryRepository.findById(target.getCategoryId())
        .orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY));

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
