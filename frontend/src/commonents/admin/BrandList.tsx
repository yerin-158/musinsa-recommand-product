import React from 'react';
import {AdminBrandResponse} from '../../model/admin/AdminBrand';
import '../../css/brandList.css';

interface BrandListProps {
  brands: AdminBrandResponse[];
  onBrandChange: (brand: AdminBrandResponse) => void;
}

const BrandList: React.FC<BrandListProps> = ({brands, onBrandChange}) => {
  return (
    <div className="brand-list">
      <h4>브랜드</h4>
      <ul>
        {brands.map((brand) => (
          <li key={brand.id} onClick={() => onBrandChange(brand)}>
            {brand.name} {brand.status == 'NOT_EXPOSED' && '(비활성)'}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BrandList;
