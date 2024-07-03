import React, {useState, useEffect} from 'react';
import {BrandSimpleResponse, ProductSetResponse} from '../../model/store/Product';
import {getAllBrands, getLowestPriceProductSet} from '../../util/apiUtils';
import '../../css/Recommend.css';

const RecommendPanel: React.FC = () => {
  const [brands, setBrands] = useState<BrandSimpleResponse[]>([]);
  const [selectedBrandId, setSelectedBrandId] = useState<number | null>(null);
  const [productSet, setProductSet] = useState<ProductSetResponse | null>(null);
  const [mixBrand, setMixBrand] = useState(false);

  useEffect(() => {
    const fetchBrands = async () => {
      try {
        const brandsResponse = await getAllBrands();
        setBrands(brandsResponse);
      } catch (err) {
        console.error('Failed to fetch brands');
      }
    };

    fetchBrands();
  }, []);

  useEffect(() => {
    const fetchProductSet = async () => {
      try {
        const productSetResponse = await getLowestPriceProductSet(
          selectedBrandId !== null,
          selectedBrandId || undefined
        );
        setProductSet(productSetResponse);
      } catch (err) {
        console.error('Failed to fetch product set');
      }
    };

    if (selectedBrandId !== null) {
      fetchProductSet();
    }
  }, [selectedBrandId]);

  const handleBrandChange = (id: number | null) => {
    setSelectedBrandId(id);
    setMixBrand(false);
  };

  const handleCombineBrand = async () => {
    handleBrandChange(null);
    setMixBrand(true);

    const productSetResponse = await getLowestPriceProductSet(false);
    setProductSet(productSetResponse);
  };

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('ko-KR', {style: 'currency', currency: 'KRW'}).format(price);
  };

  return (
    <div className="store-content">
      <div className="brand-list">
        <h4>브랜드</h4>
        <ul>
          <li onClick={handleCombineBrand}>브랜드 없음</li>
          {brands.map((brand) => {
            if (brand.status != 'EXPOSED') {
              return null;
            }

            return (
              <li key={brand.id} onClick={() => handleBrandChange(brand.id)}>
                {brand.name}
              </li>
            );
          })}
        </ul>
      </div>
      <div className="product-set">
        <h4>🚀 추천 상품</h4>
        {productSet ? (
          <div>
            <ul>
              {productSet.products.map(({product, category, brand}) => (
                <li key={product.id}>
                  [{category.name}] {mixBrand ? `[${brand.name}]` : ''} {product.name}: {formatPrice(product.price)}
                </li>
              ))}
            </ul>
            <p>
              <b>총 금액</b> 👉 {formatPrice(productSet.sumPrice)}
            </p>
          </div>
        ) : (
          <p>좌측에서 브랜드를 선택하세요.</p>
        )}
      </div>
    </div>
  );
};

export default RecommendPanel;
