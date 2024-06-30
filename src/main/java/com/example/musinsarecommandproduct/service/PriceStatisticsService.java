package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.specs.PriceStatisticsSpecs;
import com.example.musinsarecommandproduct.repository.PriceStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class PriceStatisticsService {

  private final PriceStatisticsRepository priceStatisticsRepository;

  public List<PriceStatistics> findExpensiveProductByCategoryId(Long categoryId, Integer size) {
    return this.findByCategoryId(categoryId, size, Sort.by(Sort.Direction.DESC, "price"));
  }

  public List<PriceStatistics> findCheapPriceProductByCategoryId(Long categoryId, Integer size) {
    return this.findByCategoryId(categoryId, size, Sort.by(Sort.Direction.ASC, "price"));
  }

  private List<PriceStatistics> findByCategoryId(Long categoryId, Integer size, Sort sort) {
    Specification<PriceStatistics> specification = Specification.where(PriceStatisticsSpecs.equalsCategoryId(categoryId));
    Pageable pageable = PageRequest.of(0, size, sort);
    return priceStatisticsRepository.findAll(specification, pageable).getContent();
  }

}
