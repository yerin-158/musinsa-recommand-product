package com.example.musinsarecommandproduct.controller.store.interfaces;

import com.example.musinsarecommandproduct.controller.store.dto.ProductByCategoryResponse;
import com.example.musinsarecommandproduct.enums.PriceType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Tag(name = "Product API", description = "상품 API")
public interface ProductApi {

  @GetMapping("/summary")
  @Operation(summary = "카테고리별 제품 요약 정보 조회", description = "카테고리 ID와 가격 유형을 기준으로 제품 요약 정보를 조회합니다.")
  ResponseEntity<ProductByCategoryResponse> getProductsSummaryByCategory(
      @Parameter(description = "카테고리 ID", required = true) @RequestParam("categoryId") Long categoryId,
      @Parameter(description = "가격 유형 리스트", required = true) @RequestParam("priceType") List<PriceType> priceTypes,
      @Parameter(description = "결과 크기", required = false) @RequestParam(value = "size", required = false, defaultValue = "1") Integer size);

}
