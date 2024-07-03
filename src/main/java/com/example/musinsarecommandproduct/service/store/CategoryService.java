package com.example.musinsarecommandproduct.service.store;

import com.example.musinsarecommandproduct.controller.store.dto.CategorySimpleResponse;
import com.example.musinsarecommandproduct.controller.store.mapper.CategoryMapper;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public Category findById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_CATEGORY));
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public List<CategorySimpleResponse> findAllToResponse() {
    return categoryRepository.findAll().stream().map(category -> CategoryMapper.INSTANCE.toCategorySimpleResponse(category)).collect(Collectors.toList());
  }
}
