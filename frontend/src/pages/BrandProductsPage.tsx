import React, {useState, useEffect} from 'react';
import {AdminBrandResponse} from '../model/admin/AdminBrand';
import {PageResponse} from '../model/ResponseWrapper';
import {getAllBrands, getBrandProducts} from '../util/apiUtils';
import {AdminProductFullInfoResponse} from '../model/admin/AdminProduct';

const BrandProductsPage: React.FC = () => {
  const [brands, setBrands] = useState<AdminBrandResponse[]>([]);
  const [selectedBrandId, setSelectedBrandId] = useState<number | null>(null);
  const [productFullInfo, setProductFullInfo] = useState<AdminProductFullInfoResponse[] | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchBrands = async () => {
      try {
        const brandsResponse = await getAllBrands();
        setBrands(brandsResponse);
        setError(null); // Clear any previous errors
      } catch (err) {
        setError('Failed to fetch brands');
      }
    };

    fetchBrands();
  }, []);

  useEffect(() => {
    const fetchProducts = async () => {
      if (selectedBrandId !== null) {
        try {
          const productsResponse: PageResponse<AdminProductFullInfoResponse> = await getBrandProducts(selectedBrandId);
          setProductFullInfo(productsResponse.content);
          setError(null); // Clear any previous errors
        } catch (err) {
          setError('Failed to fetch products');
        }
      }
    };

    fetchProducts();
  }, [selectedBrandId]);

  const handleBrandChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedBrandId(Number(event.target.value));
  };

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h1>Brand Products Page</h1>
      <div>
        <label htmlFor="brand-select">Select a brand:</label>
        <select id="brand-select" onChange={handleBrandChange}>
          <option value="">--Please choose a brand--</option>
          {brands.map((brand) => (
            <option key={brand.id} value={brand.id}>
              {brand.name}
            </option>
          ))}
        </select>
      </div>
      {selectedBrandId && (
        <>
          <h2>Products</h2>
          <ul>
            {productFullInfo?.map((fullInfo) => (
              <li key={fullInfo.product.id}>
                {fullInfo.product.name} - ${fullInfo.product.price}
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
};

export default BrandProductsPage;
