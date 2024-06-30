package com.example.musinsarecommandproduct.entitie;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@MappedSuperclass
abstract class BaseEntity {
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;

}
