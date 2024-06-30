package com.example.musinsarecommandproduct.controller;

import com.example.musinsarecommandproduct.controller.dto.ProductSetResponse;
import com.example.musinsarecommandproduct.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@RestController
@RequestMapping("/api/v1/recommend")
@RequiredArgsConstructor
public class RecommendController {

  private final RecommendService recommendService;

  @GetMapping("/cheapest-set")
  public ResponseEntity<ProductSetResponse> getCheapestProductSet(@RequestParam("byBrand") Boolean byBrand, @RequestParam(name = "brandId", required = false) Long brandId) {
    if (byBrand && brandId == null) {
      throw new RuntimeException();
    }

    if (byBrand) {
      ProductSetResponse productSetResponse = recommendService.getProductSetByBrand(brandId);
      return ResponseEntity.ok(productSetResponse);
    }

    ProductSetResponse productSetResponse = recommendService.getCheapProductSet();
    return ResponseEntity.ok(productSetResponse);
  }

}
