package com.example.musinsarecommandproduct.controller.admin.dto;

import java.time.LocalDateTime;

/**
 * Created by yerin-158 on 7/1/24.
 *
 * @author yerin-158
 * @version 7/1/24.
 * @implNote First created
 */
public record AdminCategoryResponse(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {}
