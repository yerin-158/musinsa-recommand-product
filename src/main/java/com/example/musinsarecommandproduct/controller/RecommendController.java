package com.example.musinsarecommandproduct.controller;

import com.example.musinsarecommandproduct.controller.dto.ProductSetResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@RestController("/api/v1/recommend")
public class RecommendController {

  @GetMapping("/cheapest-set")
  public ResponseEntity<ProductSetResponse> getCheapestProductSet(@Param("byBrand") Boolean byBrand) {
    return ResponseEntity.ok(new ProductSetResponse());
  }

}
