package com.example.musinsarecommandproduct.controller.admin.dto;

import com.example.musinsarecommandproduct.enums.ProductStatus;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public record AdminProductAddRequest(Long categoryId, String name, Integer price, ProductStatus status) {}
