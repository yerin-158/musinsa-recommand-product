package com.example.musinsarecommandproduct.controller.admin.dto;

import com.example.musinsarecommandproduct.enums.ProductStatus;

import java.time.LocalDateTime;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
public record AdminSimpleProductResponse(
    Long id, Long brandId, Long categoryId, String name, Integer price, ProductStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
