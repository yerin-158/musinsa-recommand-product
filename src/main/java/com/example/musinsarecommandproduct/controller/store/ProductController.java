package com.example.musinsarecommandproduct.controller.store;

import com.example.musinsarecommandproduct.controller.store.dto.ProductByCategoryResponse;
import com.example.musinsarecommandproduct.controller.store.interfaces.ProductApi;
import com.example.musinsarecommandproduct.enums.PriceType;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.service.store.ProductService;
import lombok.RequiredArgsConstructor;
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
public class ProductController implements ProductApi {

  private final ProductService productService;

  @GetMapping("/summary")
  public ResponseEntity<ProductByCategoryResponse> getProductsSummaryByCategory(
      @RequestParam(value = "categoryId", required = false) Long categoryId,
      @RequestParam(value = "priceType", required = false) List<PriceType> priceTypes,
      @RequestParam(value = "size", required = false, defaultValue = "1") Integer size) {

    if (categoryId == null || priceTypes == null || priceTypes.isEmpty()) {
      throw new BadRequestException(BadRequestType.INVALID_REQUEST);
    }

    ProductByCategoryResponse response = productService.getProductByCategory(categoryId, priceTypes, size);
    return ResponseEntity.ok(response);
  }

}
