package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException());
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
