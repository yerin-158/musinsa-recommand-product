package com.example.musinsarecommandproduct.controller.store.dto;

import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

/**
 * Created by yerin-158 on 7/2/24.
 *
 * @author yerin-158
 * @version 7/2/24.
 * @implNote First created
 */
public record PageResponse<T>(
    int totalPages,
    long totalElements,
    int size,
    int page,
    boolean isFirst,
    boolean isLast,
    boolean isEmpty,
    List<T> content
) {
  public PageResponse(List<T> content, Pageable pageable, long totalElements) {
    this(
        (int) Math.ceil((double) totalElements / pageable.getPageSize()),
        totalElements,
        pageable.getPageSize(),
        pageable.getPageNumber(),
        pageable.getPageNumber() == 0,
        pageable.getPageNumber() == (int) Math.ceil((double) totalElements / pageable.getPageSize()) - 1,
        content.isEmpty(),
        content
    );
  }

  public static <T> PageResponse<T> empty(Pageable pageable) {
    return new PageResponse<>(
        Collections.emptyList(),
        pageable,
        0
    );
  }
}
