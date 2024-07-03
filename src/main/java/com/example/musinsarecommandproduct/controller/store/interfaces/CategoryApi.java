package com.example.musinsarecommandproduct.controller.store.interfaces;

import com.example.musinsarecommandproduct.controller.store.dto.CategorySimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by yerin-158 on 7/3/24.
 *
 * @author yerin-158
 * @version 7/3/24.
 * @implNote First created
 */
@Tag(name = "Category API", description = "카테고리 API")
public interface CategoryApi {

  @GetMapping
  @Operation(summary = "카테고리 리스트 가져오기")
  ResponseEntity<List<CategorySimpleResponse>> getCategories();

}
