import React from 'react';
import {AdminBrandResponse} from '../model/admin/AdminBrand';

interface BrandListProps {
  brands: AdminBrandResponse[];
  onBrandChange: (id: number) => void;
  onAddBrand: () => void;
  newBrandName: string;
  setNewBrandName: (name: string) => void;
}

const BrandList: React.FC<BrandListProps> = ({brands, onBrandChange, onAddBrand, newBrandName, setNewBrandName}) => {
  return (
    <div>
      <ul>
        {brands.map((brand) => (
          <li key={brand.id} onClick={() => onBrandChange(brand.id)}>
            {brand.name}
          </li>
        ))}
      </ul>
      <input
        type="text"
        value={newBrandName}
        onChange={(e) => setNewBrandName(e.target.value)}
        placeholder="New Brand Name"
      />
      <button onClick={onAddBrand}>브랜드 추가하기</button>
    </div>
  );
};

export default BrandList;
