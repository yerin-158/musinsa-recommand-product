package com.example.musinsarecommandproduct.service;

import com.example.musinsarecommandproduct.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepository;

}
