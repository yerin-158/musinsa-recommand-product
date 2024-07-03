import React, {useCallback, useEffect, useMemo, useState} from 'react';
import {AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import '../../css/brandProducts.css';
import Pagination from '../common/Pagination';
import useFetchCategories from '../../hooks/useFetchCategories';
import {CategoryResponse} from '../../model/store/Category';
import {BrandStatus} from '../../model/types';
import {convertProductStatus} from '../../util/textUtils';

interface BrandProductsProps {
  products: AdminProductFullInfoResponse[] | null;
  onProductClick: (product: AdminProductFullInfoResponse) => void;
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
  changeBrandStatus: (status: BrandStatus) => void;
  brandIsActive: boolean;
}

const BrandProducts: React.FC<BrandProductsProps> = ({
  products,
  onProductClick,
  currentPage,
  totalPages,
  onPageChange,
  changeBrandStatus,
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
  }, []);

  const canActivateBrand = useMemo(() => {
    if (!products || products.length < categories.length) {
      return false;
    }
    const categoryIdsWithProducts = new Set(products.map((product) => product.product.categoryId));
    return categories.every((category) => categoryIdsWithProducts.has(category.id));
  }, [products]);

  const renderUpdateBrandStatusButton = useCallback(() => {
    if (!products) {
      return null;
    }

    if (brandIsActive) {
      return <button onClick={() => changeBrandStatus('NOT_EXPOSED')}>브랜드 비활성화</button>;
    }

    return (
      <button
        className="brand-active-button"
        style={{backgroundColor: canActivateBrand ? '#007bff' : undefined}}
        onClick={() => changeBrandStatus('EXPOSED')}
        disabled={!canActivateBrand}>
        브랜드 활성화
      </button>
    );
  }, [products, brandIsActive]);

  return (
    <div className="product-list">
      <div className="product-list-header">
        <h4>브랜드 상품</h4>
        {renderUpdateBrandStatusButton()}
      </div>
      <ul>
        {products?.map((product) => (
          <li key={product.product.id} onClick={() => onProductClick(product)}>
            [{product.category.name}] {product.product.name} - ₩{product.product.price.toLocaleString('ko-KR')}
            <span style={{fontSize: '12px', color: product.product.status === 'DELETED' ? 'red' : '#007bff'}}>
              {' '}
              ({convertProductStatus(product.product.status)})
            </span>
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
