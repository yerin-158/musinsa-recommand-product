package com.example.musinsarecommandproduct.repository;

import com.example.musinsarecommandproduct.entitie.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

}
