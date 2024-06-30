package com.example.musinsarecommandproduct.controller.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandResponse;
import com.example.musinsarecommandproduct.service.admin.AdminBrandService;
import lombok.RequiredArgsConstructor;
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

  @PostMapping
  public ResponseEntity<AdminBrandResponse> add(@RequestBody AdminBrandAddRequest request) {
    return ResponseEntity.ok(new AdminBrandResponse());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdminBrandResponse> add(@PathVariable("id") Long id) {
    return ResponseEntity.ok(new AdminBrandResponse());
  }

}
