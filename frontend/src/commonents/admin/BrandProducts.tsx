import React, {useEffect, useMemo, useState} from 'react';
import {AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import '../../css/brandProducts.css';
import Pagination from '../common/Pagination';
import useFetchCategories from '../../hooks/useFetchCategories';
import {CategoryResponse} from '../../model/store/Category';

interface BrandProductsProps {
  products: AdminProductFullInfoResponse[] | null;
  onProductClick: (product: AdminProductFullInfoResponse) => void;
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
  onActivateBrand: () => void;
  brandIsActive: boolean;
}

const BrandProducts: React.FC<BrandProductsProps> = ({
  products,
  onProductClick,
  currentPage,
  totalPages,
  onPageChange,
  onActivateBrand,
  brandIsActive,
}) => {
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const fetchCategories = useFetchCategories();

  useEffect(() => {
    const getCategories = async () => {
      const response = await fetchCategories({
        errorCallback: () => console.error('Failed to fetch categories'),
      });
      setCategories(response || []);
    };

    getCategories();
  }, [fetchCategories]);

  const canActivateBrand = useMemo(() => {
    if (!products || products.length < categories.length) {
      return false;
    }
    const categoryIdsWithProducts = new Set(products.map((product) => product.product.categoryId));
    return categories.every((category) => categoryIdsWithProducts.has(category.id));
  }, [products]);

  return (
    <div className="product-list">
      <div className="product-list-header">
        <h4>브랜드 상품</h4>
        {!brandIsActive && products && (
          <button onClick={onActivateBrand} disabled={!canActivateBrand}>
            브랜드 활성화
          </button>
        )}
      </div>
      <ul>
        {products?.map((product) => (
          <li key={product.product.id} onClick={() => onProductClick(product)}>
            [{product.category.name}] {product.product.name} - ₩{product.product.price.toLocaleString('ko-KR')}
          </li>
        ))}
      </ul>
      {!products && <p>좌측에서 브랜드를 선택하세요.</p>}
      {products && products.length === 0 ? <p>🥹 아직 등록된 상품이 없습니다.</p> : null}
      {products && products.length > 0 && (
        <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={onPageChange} />
      )}
    </div>
  );
};

export default BrandProducts;
