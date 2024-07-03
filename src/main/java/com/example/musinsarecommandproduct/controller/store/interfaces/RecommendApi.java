package com.example.musinsarecommandproduct.controller.store.interfaces;

import com.example.musinsarecommandproduct.controller.store.dto.ProductSetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
@Tag(name = "Recommend API", description = "최소가 상품 셋트 추천 API")
public interface RecommendApi {

  @GetMapping("/products")
  @Operation(summary = "추천 제품 조회", description = "특정 조건에 따라 추천 제품 목록을 조회합니다.")
  ResponseEntity<ProductSetResponse> getLowestPriceProductSet(
      @Parameter(description = "브랜드 지정 유무", required = false) @RequestParam(name = "byBrand", defaultValue = "false", required = false) Boolean byBrand,
      @Parameter(description = "byBrand = true인 경우, 브랜드id 필수값", required = false) @RequestParam(name = "brandId", required = false) Long brandId);

}
