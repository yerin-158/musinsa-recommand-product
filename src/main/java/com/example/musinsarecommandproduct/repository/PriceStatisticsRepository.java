package com.example.musinsarecommandproduct.repository;

import com.example.musinsarecommandproduct.entitie.PriceStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public interface PriceStatisticsRepository extends JpaRepository<PriceStatistics, Long>, JpaSpecificationExecutor<PriceStatistics> {



}
