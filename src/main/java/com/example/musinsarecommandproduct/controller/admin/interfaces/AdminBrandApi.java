package com.example.musinsarecommandproduct.controller.admin.interfaces;

import com.example.musinsarecommandproduct.controller.admin.dto.*;
import com.example.musinsarecommandproduct.controller.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Tag(name = "Admin Product API", description = "[Admin] 브랜드 및 제품 관리 API")
public interface AdminBrandApi {

  @PostMapping
  @Operation(summary = "브랜드 추가", description = "새로운 브랜드를 추가합니다.")
  ResponseEntity<AdminBrandResponse> add(
      @Parameter(description = "브랜드 추가 요청 객체", required = true) @RequestBody AdminBrandAddRequest request) throws BadRequestException;

  @GetMapping("/{id}")
  @Operation(summary = "브랜드 조회", description = "ID로 브랜드를 조회합니다.")
  ResponseEntity<AdminBrandResponse> findOne(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId);

  @PostMapping("/{id}/products")
  @Operation(summary = "브랜드에 제품 추가", description = "브랜드 ID로 제품을 추가합니다.")
  ResponseEntity<AdminProductResponse> addProduct(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId,
      @Parameter(description = "제품 추가 요청 객체", required = true) @RequestBody AdminProductAddRequest request);

  @GetMapping("/{id}/products/{productId}")
  @Operation(summary = "브랜드의 특정 제품 조회", description = "브랜드 ID와 제품 ID로 특정 제품을 조회합니다.")
  ResponseEntity<AdminProductResponse> findProduct(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId,
      @Parameter(description = "제품 ID", required = true) @PathVariable("productId") Long productId);

  @GetMapping("/{id}/products")
  @Operation(summary = "브랜드의 모든 제품 조회", description = "브랜드 ID로 모든 제품을 조회합니다.")
  ResponseEntity<PageResponse<AdminProductResponse>> findProducts(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId,
      @Parameter(description = "페이지 번호", required = false) @RequestParam(name = "page", defaultValue = "0") Integer page,
      @Parameter(description = "페이지 크기", required = false) @RequestParam(name = "size", defaultValue = "20") Integer size);

  @PutMapping("/{id}/products/{productId}")
  @Operation(summary = "브랜드의 특정 제품 수정", description = "브랜드 ID와 제품 ID로 특정 제품을 수정합니다.")
  ResponseEntity<AdminProductResponse> modifyProduct(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId,
      @Parameter(description = "제품 ID", required = true) @PathVariable("productId") Long productId,
      @Parameter(description = "제품 수정 요청 객체", required = true) @RequestBody AdminProductModifyRequest request);

  @PutMapping("/{id}/products/{productId}/status")
  @Operation(summary = "브랜드의 특정 제품 상태 수정", description = "브랜드 ID와 제품 ID로 특정 제품의 상태를 수정합니다.")
  ResponseEntity<AdminProductResponse> modifyProductStatus(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId,
      @Parameter(description = "제품 ID", required = true) @PathVariable("productId") Long productId,
      @Parameter(description = "제품 상태 수정 요청 객체", required = true) @RequestBody AdminProductStatusModifyRequest request);

  @DeleteMapping("/{id}/products/{productId}")
  @Operation(summary = "브랜드의 특정 제품 삭제", description = "브랜드 ID와 제품 ID로 특정 제품을 삭제합니다.")
  ResponseEntity<AdminProductResponse> deleteProduct(
      @Parameter(description = "브랜드 ID", required = true) @PathVariable("id") Long brandId,
      @Parameter(description = "제품 ID", required = true) @PathVariable("productId") Long productId);
}
