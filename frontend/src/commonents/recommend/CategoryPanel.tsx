import React, {useState, useEffect} from 'react';
import {ProductByCategoryResponse} from '../../model/store/Product';
import {getProductsSummaryByCategory} from '../../util/apiUtils';
import '../../css/Recommend.css';
import useFetchCategories from '../../hooks/useFetchCategories';
import {CategoryResponse} from '../../model/store/Category';

const CategoryPanel: React.FC = () => {
  const [products, setProducts] = useState<ProductByCategoryResponse | null>(null);
  const [categoryId, setCategoryId] = useState<number>(0);
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const fetchCategories = useFetchCategories();

  useEffect(() => {
    const fetchProductsByCategory = async () => {
      try {
        const productsByCategory = await getProductsSummaryByCategory(categoryId, ['HIGH', 'LOW']);
        setProducts(productsByCategory);
      } catch (err) {
        console.error('Failed to fetch product');
      }
    };

    if (!!categoryId && categoryId > 0) {
      fetchProductsByCategory();
    }
  }, [categoryId]);

  useEffect(() => {
    const getCategories = async () => {
      const response = await fetchCategories({
        errorCallback: () => console.error('Failed to fetch categories'),
      });
      setCategories(response || []);
    };

    getCategories();
  }, []);

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR', {style: 'currency', currency: 'KRW'}).format(price);
  };

  return (
    <div className="store-content">
      <div className="brand-list">
        <h4>카테고리</h4>
        <ul>
          {categories?.map((category) => (
            <li key={category.id} onClick={() => setCategoryId(category.id)}>
              {category.name}
            </li>
          ))}
        </ul>
        {!categories && <ul>카테고리를 가져오는데 실패했어요.</ul>}
      </div>
      <div className="product-set">
        <h4>🔎 가격 비교</h4>
        {products ? (
          <div>
            <ul>
              {products.highestPriceProducts?.map(({product, brand}, index) => (
                <React.Fragment key={product.id}>
                  {index == 0 && <b>가장 비싼 상품</b>}
                  <li>
                    [{brand.name}] {product.name}: {formatPrice(product.price)}
                  </li>
                </React.Fragment>
              ))}
              {products.lowestPriceProducts?.map(({product, brand}, index) => (
                <React.Fragment key={product.id}>
                  {index == 0 && <b>가장 저렴한 상품</b>}
                  <li>
                    [{brand.name}] {product.name}: {formatPrice(product.price)}
                  </li>
                </React.Fragment>
              ))}
            </ul>
          </div>
        ) : (
          <p>좌측에서 카테고리를 선택하세요.</p>
        )}
      </div>
    </div>
  );
};

export default CategoryPanel;
