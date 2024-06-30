package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import com.example.musinsarecommandproduct.entitie.specs.PriceStatisticsSpecs;
import com.example.musinsarecommandproduct.enums.PriceType;
import com.example.musinsarecommandproduct.repository.PriceStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public List<PriceStatistics> find(Long categoryId, PriceType priceType, Integer size) {
    if (priceType.equals(PriceType.CHEAP)) {
      return this.findByCategoryId(categoryId, size, Sort.by(Sort.Direction.ASC, "lowestPrice"));
    }

    if (priceType.equals(PriceType.EXPENSIVE)) {
      return this.findByCategoryId(categoryId, size, Sort.by(Sort.Direction.DESC, "highestPrice"));
    }
    return new ArrayList<>();
  }

  private List<PriceStatistics> findByCategoryId(Long categoryId, Integer size, Sort sort) {
    Specification<PriceStatistics> specification = Specification.where(PriceStatisticsSpecs.equalsCategoryId(categoryId));
    Pageable pageable = PageRequest.of(0, size, sort);
    return priceStatisticsRepository.findAll(specification, pageable).getContent();
  }

}
