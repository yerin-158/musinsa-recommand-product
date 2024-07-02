import React, {useState, useEffect} from 'react';
import {AdminBrandResponse} from '../../model/admin/AdminBrand';
import {ProductByCategoryResponse, ProductSetResponse} from '../../model/store/Product';
import {getAllBrands, getLowestPriceProductSet, getProductsSummaryByCategory} from '../../util/apiUtils';
import '../../css/Recommend.css';

const CategoryPanel: React.FC = () => {
  // const [brands, setBrands] = useState<AdminBrandResponse[]>([]);
  const [products, setProducts] = useState<ProductByCategoryResponse | null>(null);
  const [categoryId, setCategoryId] = useState<number>(0);

  // useEffect(() => {
  //   const fetchBrands = async () => {
  //     try {
  //       const brandsResponse = await getAllBrands();
  //       setBrands(brandsResponse);
  //     } catch (err) {
  //       console.error('Failed to fetch brands');
  //     }
  //   };

  //   fetchBrands();
  // }, []);

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

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR', {style: 'currency', currency: 'KRW'}).format(price);
  };

  const categories = [
    {id: 1, name: '상의'},
    {id: 2, name: '아우터'},
    {id: 3, name: '바지'},
    {id: 4, name: '스니커즈'},
    {id: 5, name: '가방'},
    {id: 6, name: '모자'},
    {id: 7, name: '양말'},
    {id: 8, name: '악세서리'},
  ];

  return (
    <div className="store-content">
      <div className="brand-list">
        <h4>카테고리</h4>
        <ul>
          {categories.map((category) => (
            <li key={category.id} onClick={() => setCategoryId(category.id)}>
              {category.name}
            </li>
          ))}
        </ul>
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
