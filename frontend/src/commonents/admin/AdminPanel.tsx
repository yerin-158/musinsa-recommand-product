import React, {useState, useEffect} from 'react';
import {AdminBrandAddRequest, AdminBrandResponse} from '../../model/admin/AdminBrand';
import {AdminProductAddRequest, AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import {getAllBrands, getBrandProducts, addBrand, addProductToBrand} from '../../util/apiUtils';
import BrandProducts from './BrandProducts';
import BrandList from './BrandList';

const AdminPanel: React.FC = () => {
  const [brands, setBrands] = useState<AdminBrandResponse[]>([]);
  const [selectedBrandId, setSelectedBrandId] = useState<number | null>(null);
  const [productFullInfo, setProductFullInfo] = useState<AdminProductFullInfoResponse[] | null>(null);
  const [newBrandName, setNewBrandName] = useState<string>('');
  const [newProduct, setNewProduct] = useState<AdminProductAddRequest>({
    name: '',
    price: 0,
    categoryId: 0,
    brandId: 0,
  });

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
    const fetchProducts = async () => {
      if (selectedBrandId !== null) {
        try {
          const productsResponse = await getBrandProducts(selectedBrandId);
          setProductFullInfo(productsResponse.content);
        } catch (err) {
          console.error('Failed to fetch products');
        }
      }
    };

    fetchProducts();
  }, [selectedBrandId]);

  const handleBrandChange = (id: number) => {
    setSelectedBrandId(id);
  };

  const handleAddBrand = async () => {
    try {
      await addBrand({name: newBrandName});
      setNewBrandName('');
      const brandsResponse = await getAllBrands();
      setBrands(brandsResponse);
    } catch (err) {
      console.error('Failed to add brand');
    }
  };

  const handleAddProduct = async () => {
    if (selectedBrandId) {
      try {
        await addProductToBrand(selectedBrandId, newProduct);
        const productsResponse = await getBrandProducts(selectedBrandId);
        setProductFullInfo(productsResponse.content);
        setNewProduct({name: '', price: 0, categoryId: 0, brandId: 0});
      } catch (err) {
        console.error('Failed to add product');
      }
    }
  };

  return (
    <div>
      <BrandList
        brands={brands}
        onBrandChange={handleBrandChange}
        onAddBrand={handleAddBrand}
        newBrandName={newBrandName}
        setNewBrandName={setNewBrandName}
      />
      {selectedBrandId && (
        <BrandProducts
          products={productFullInfo}
          onAddProduct={handleAddProduct}
          newProduct={newProduct}
          setNewProduct={setNewProduct}
        />
      )}
    </div>
  );
};

export default AdminPanel;
