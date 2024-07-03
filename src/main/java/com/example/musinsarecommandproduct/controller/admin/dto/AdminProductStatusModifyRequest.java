package com.example.musinsarecommandproduct.controller.admin.dto;

import com.example.musinsarecommandproduct.enums.ProductStatus;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
public record AdminProductStatusModifyRequest(ProductStatus status) {}
