package com.example.musinsarecommandproduct.repository;

import com.example.musinsarecommandproduct.entitie.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
