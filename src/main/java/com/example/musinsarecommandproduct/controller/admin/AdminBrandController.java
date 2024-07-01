package com.example.musinsarecommandproduct.controller.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.*;
import com.example.musinsarecommandproduct.service.admin.AdminBrandService;
import com.example.musinsarecommandproduct.service.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@RestController
@RequestMapping("/admin/api/v1/brands")
@RequiredArgsConstructor
public class AdminBrandController {

  private final AdminBrandService adminBrandService;
  private final AdminProductService adminProductService;

  @PostMapping
  public ResponseEntity<AdminBrandResponse> add(@RequestBody AdminBrandAddRequest request) throws BadRequestException {
    AdminBrandResponse response = adminBrandService.add(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdminBrandResponse> add(@PathVariable("id") Long brandId) {
    AdminBrandResponse response = adminBrandService.findOne(brandId);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/products")
  public ResponseEntity<AdminProductResponse> add(@PathVariable("id") Long brandId, @RequestBody AdminProductAddRequest request) {
    AdminProductResponse response = adminProductService.add(brandId, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}/products/{productId}")
  public ResponseEntity<AdminProductResponse> findOne(@PathVariable("id") Long brandId, @PathVariable("productId") Long productId) {
    AdminProductResponse response = adminProductService.findOne(brandId, productId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}/products")
  public ResponseEntity<Page<AdminProductResponse>> findOne(
      @PathVariable("id") Long brandId, @PathVariable("productId") Long productId,
      @RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "20") Integer size) {
    return ResponseEntity.ok(null);
  }

  @PutMapping("/{id}/products/{productId}")
  public ResponseEntity<AdminProductResponse> modify(@PathVariable("id") Long brandId, @PathVariable("productId") Long productId, @RequestBody AdminProductModifyRequest request) {
    AdminProductResponse response = adminProductService.modifyDetails(brandId, productId, request);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/products/{productId}/status")
  public ResponseEntity<AdminProductResponse> modify(@PathVariable("id") Long brandId, @PathVariable("productId") Long productId, @RequestBody AdminProductStatusModifyRequest request) {
    AdminProductResponse response = adminProductService.modifyStatus(brandId, productId, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}/products/{productId}")
  public ResponseEntity<AdminProductResponse> delete(@PathVariable("id") Long brandId, @PathVariable("productId") Long productId) {
    return ResponseEntity.ok(null);
  }




}
