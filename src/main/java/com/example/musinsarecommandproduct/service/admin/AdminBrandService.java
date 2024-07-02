package com.example.musinsarecommandproduct.service.admin;

import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandAddRequest;
import com.example.musinsarecommandproduct.controller.admin.dto.AdminBrandResponse;
import com.example.musinsarecommandproduct.controller.admin.mapper.AdminBrandMapper;
import com.example.musinsarecommandproduct.entitie.Brand;
import com.example.musinsarecommandproduct.entitie.specs.BrandSpecs;
import com.example.musinsarecommandproduct.exception.BadRequestException;
import com.example.musinsarecommandproduct.exception.BadRequestType;
import com.example.musinsarecommandproduct.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

  public AdminBrandResponse add(AdminBrandAddRequest request)  {
    //TODO validation 분리
    Specification<Brand> specification = Specification.where(BrandSpecs.equalsName(request.name()));
    Optional<Brand> sameNameBrand = brandRepository.findOne(specification);

    if (sameNameBrand.isPresent()) {
      throw new BadRequestException(BadRequestType.DUPLICATE_BRAND_NAME);
    }

    Brand newBrand = AdminBrandMapper.INSTANCE.toBrand(request);
    brandRepository.save(newBrand);

    return AdminBrandMapper.INSTANCE.toAdminBrandResponse(newBrand);
  }

  public List<AdminBrandResponse> findAll() {
    return brandRepository.findAll().stream().map(brand -> AdminBrandMapper.INSTANCE.toAdminBrandResponse(brand)).collect(Collectors.toList());
  }

  public AdminBrandResponse findOne(Long id) {
    Brand brand = brandRepository.findById(id).orElseThrow(() -> new BadRequestException(BadRequestType.NOT_FOUND_BRAND));
    return AdminBrandMapper.INSTANCE.toAdminBrandResponse(brand);
  }

}
