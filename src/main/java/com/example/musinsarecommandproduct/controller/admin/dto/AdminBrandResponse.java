package com.example.musinsarecommandproduct.controller.admin.dto;

import com.example.musinsarecommandproduct.enums.BrandStatus;

import java.time.LocalDateTime;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public record AdminBrandResponse (Long id, String name, BrandStatus status, LocalDateTime createdAt, LocalDateTime updatedAt){}
