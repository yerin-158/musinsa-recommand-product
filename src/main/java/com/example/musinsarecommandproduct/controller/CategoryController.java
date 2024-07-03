package com.example.musinsarecommandproduct.controller;

import com.example.musinsarecommandproduct.controller.dto.CategorySimpleResponse;
import com.example.musinsarecommandproduct.controller.interfaces.CategoryApi;
import com.example.musinsarecommandproduct.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yerin-158 on 7/3/24.
 *
 * @author yerin-158
 * @version 7/3/24.
 * @implNote First created
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategorySimpleResponse>> getCategories() {
    List<CategorySimpleResponse> responses = categoryService.findAllToResponse();
    return ResponseEntity.ok(responses);
  }

}
