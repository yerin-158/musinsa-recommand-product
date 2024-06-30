package com.example.musinsarecommandproduct.controller;

import com.example.musinsarecommandproduct.controller.dto.ProductByCategoryResponse;
import com.example.musinsarecommandproduct.controller.dto.ProductSetResponse;
import com.example.musinsarecommandproduct.enums.PriceType;
import com.example.musinsarecommandproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/categories/{categoryId}/summary")
  public ResponseEntity<ProductByCategoryResponse> getProductsSummaryByCategory(
      @PathVariable("categoryId") Long categoryId,
      @RequestParam("priceType") List<PriceType> priceTypes,
      @RequestParam(value = "size", required = false, defaultValue = "1") Integer size) {

    if (categoryId == null || priceTypes == null || priceTypes.isEmpty()) {
      throw new RuntimeException();
    }

    ProductByCategoryResponse response = productService.getProductByCategory(categoryId, priceTypes, size);
    return ResponseEntity.ok(response);
  }

}
