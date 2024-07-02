import React, {useState, useEffect} from 'react';
import {AdminBrandResponse} from '../model/admin/AdminBrand';
import {ProductSetResponse} from '../model/store/Product';
import {getAllBrands, getLowestPriceProductSet} from '../util/apiUtils';

const RecommendPanel: React.FC = () => {
  const [brands, setBrands] = useState<AdminBrandResponse[]>([]);
  const [selectedBrandId, setSelectedBrandId] = useState<number | null>(null);
  const [productSet, setProductSet] = useState<ProductSetResponse | null>(null);

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
  };

  return (
    <div>
      <div>
        <ul>
          <li onClick={() => handleBrandChange(null)}>브랜드 없음</li>
          {brands.map((brand) => (
            <li key={brand.id} onClick={() => handleBrandChange(brand.id)}>
              {brand.name}
            </li>
          ))}
        </ul>
      </div>
      <div>
        {productSet ? (
          <div>
            <h2>Product Set</h2>
            <ul>
              {productSet.products.map((product) => (
                <li key={product.product.id}>
                  {product.product.name} - ${product.product.price}
                </li>
              ))}
            </ul>
            <p>Total Price: ${productSet.sumPrice}</p>
          </div>
        ) : (
          <p>Select a brand to view products</p>
        )}
      </div>
    </div>
  );
};

export default RecommendPanel;
