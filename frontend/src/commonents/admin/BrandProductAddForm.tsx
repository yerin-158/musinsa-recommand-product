import React, {useEffect, useState} from 'react';
import '../../css/admin.css';
import {AdminProductAddRequest} from '../../model/admin/AdminProduct';
import {addBrand, addProductToBrand} from '../../util/apiUtils';
import ErrorBox from '../common/ErrorBox';
import {AdminBrandResponse} from '../../model/admin/AdminBrand';
import {CategoryResponse} from '../../model/store/Category';
import useFetchCategories from '../../hooks/useFetchCategories';

interface BrandProductAddFormProps {
  selectedBrand: AdminBrandResponse | undefined;
  handleAddBrandSuccess: () => void;
  handleAddProductSuccess: () => void;
}

interface ProductFormProps extends Pick<BrandProductAddFormProps, 'selectedBrand' | 'handleAddProductSuccess'> {
  setError: (error: string) => void;
}

const ProductForm: React.FC<ProductFormProps> = ({selectedBrand, setError, handleAddProductSuccess}) => {
  const [newProduct, setNewProduct] = useState<AdminProductAddRequest>({
    name: '',
    price: 0,
    categoryId: 1,
    brandId: 0,
    status: 'EXPOSED',
  });
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

  const handleAddProduct = async () => {
    if (selectedBrand && selectedBrand.id > 0) {
      try {
        await addProductToBrand(selectedBrand.id, newProduct);
        setNewProduct({name: '', price: 0, categoryId: 1, brandId: 1, status: 'EXPOSED'});
        handleAddProductSuccess();
      } catch (err) {
        setError('Failed to add product');
      }
    }
  };

  if (selectedBrand && selectedBrand.id > 0) {
    return (
      <>
        <h5 style={{marginBottom: '0px', paddingBottom: '0px'}}>✅ {selectedBrand.name}</h5>
        <div className="form-box">
          <div className="input-group">
            <input
              type="text"
              value={newProduct.name}
              onChange={(e) => setNewProduct({...newProduct, name: e.target.value})}
              placeholder="Product Name"
            />
            <input
              type="text"
              value={newProduct.price}
              onChange={(e) => setNewProduct({...newProduct, price: +e.target.value})}
              placeholder="Product Price"
            />
            <select
              value={newProduct.categoryId}
              onChange={(e) => setNewProduct({...newProduct, categoryId: +e.target.value})}>
              {categories.map(({id, name}) => (
                <option value={id}>{name}</option>
              ))}
            </select>
            <button onClick={handleAddProduct}>상품 추가하기</button>
          </div>
        </div>
      </>
    );
  }

  return null;
};

const BrandProductAddForm: React.FC<BrandProductAddFormProps> = ({
  selectedBrand,
  handleAddBrandSuccess,
  handleAddProductSuccess,
}) => {
  const [newBrandName, setNewBrandName] = useState('');
  const [error, setError] = useState<string | null>(null);

  const handleAddBrand = async () => {
    try {
      await addBrand({name: newBrandName});
      setNewBrandName('');
      handleAddBrandSuccess();
    } catch (err) {
      setError('Failed to add brand');
    }
  };

  if (error) {
    return <ErrorBox error={error} />;
  }

  return (
    <>
      <div className="form-box">
        <div className="input-group">
          <input
            type="text"
            value={newBrandName}
            onChange={(e) => setNewBrandName(e.target.value)}
            placeholder="New Brand Name"
          />
          <button onClick={handleAddBrand}>브랜드 추가하기</button>
        </div>
      </div>
      <ProductForm
        selectedBrand={selectedBrand}
        setError={setError}
        handleAddProductSuccess={handleAddProductSuccess}
      />
    </>
  );
};

export default BrandProductAddForm;
