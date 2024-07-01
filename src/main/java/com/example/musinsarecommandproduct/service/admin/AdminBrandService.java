package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandResponse;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminProductResponse;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminBrandMapper;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminProductMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.Category;
import com.example.musinsarecommandproduct.entitie.Product;
import com.example.musinsarecommandproduct.entitie.specs.BrandSpecs;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import com.example.musinsarecommandproduct.repository.CategoryRepository;
import com.example.musinsarecommandproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yerin-158 on 6/30/24.
 *
 * @author yerin-158
 * @version 6/30/24.
 * @implNote First created
 */
@Service
@RequiredArgsConstructor
public class AdminBrandService {

  private final BrandRepository brandRepository;

  public AdminBrandResponse add(AdminBrandAddRequest request) throws BadRequestException {
    //TODO validation 분리
    Specification<Brand> specification = Specification.where(BrandSpecs.equalsName(request.name()));
    Optional<Brand> sameNameBrand = brandRepository.findOne(specification);

    if (sameNameBrand.isPresent()) {
      // 같은 이름이면 저장 불가
      throw new BadRequestException();
    }

    Brand newBrand = AdminBrandMapper.INSTANCE.toBrand(request);
    brandRepository.save(newBrand);

    return AdminBrandMapper.INSTANCE.toAdminBrandResponse(newBrand);
  }

  public AdminBrandResponse findOne(Long id) {
    Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return AdminBrandMapper.INSTANCE.toAdminBrandResponse(brand);
  }

}
