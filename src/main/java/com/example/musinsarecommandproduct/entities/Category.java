package com.example.musinsarecommandproduct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Entity(name = "categories")
public class Category extends BaseEntity{

  @Id
  private Long id;

  private String name;

}
