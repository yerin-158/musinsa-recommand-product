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
    if (categoryId == null || priceType == null || size == null) {
      throw new RuntimeException();
    }

    if (priceType.equals(PriceType.CHEAP)) {
      return this.retrieve(categoryId, null, size, Sort.by(Sort.Direction.ASC, "lowestPrice"));
    }

    if (priceType.equals(PriceType.EXPENSIVE)) {
      return this.retrieve(categoryId, null, size, Sort.by(Sort.Direction.DESC, "highestPrice"));
    }
    return new ArrayList<>();
  }

  public List<PriceStatistics> retrieve(Long categoryId, Long brandId) {
    Specification<PriceStatistics> specification = getSpecification(categoryId, brandId);

    return priceStatisticsRepository.findAll(specification);
  }

  public List<PriceStatistics> retrieve(Long categoryId, Long brandId, Integer size, Sort sort) {
    Specification<PriceStatistics> specification = getSpecification(categoryId, brandId);

    Pageable pageable = PageRequest.of(0, size, sort);
    return priceStatisticsRepository.findAll(specification, pageable).getContent();
  }

  private static Specification<PriceStatistics> getSpecification(Long categoryId, Long brandId) {
    if (categoryId == null && brandId == null) {
      throw new RuntimeException();
    }

    Specification<PriceStatistics> specification = Specification.where(null);

    if (categoryId != null) {
      specification = specification.and(PriceStatisticsSpecs.equalsCategoryId(categoryId));
    }

    if (brandId != null) {
      specification = specification.and(PriceStatisticsSpecs.equalsBrandId(brandId));
    }
    return specification;
  }

}
