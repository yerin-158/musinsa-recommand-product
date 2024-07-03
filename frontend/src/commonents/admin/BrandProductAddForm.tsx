import React, {useEffect, useState} from 'react';
import '../../css/admin.css';
import {AdminProductAddRequest} from '../../model/admin/AdminProduct';
import {addBrand, addProductToBrand} from '../../util/apiUtils';
import ErrorBox from '../common/ErrorBox';
import {AdminBrandResponse} from '../../model/admin/AdminBrand';
import {CategoryResponse} from '../../model/store/Category';
import useFetchCategories from '../../hooks/useFetchCategories';
import {BadRequestErrorResponse} from '../../model/common/BadRequestErrorResponse';
import {AxiosError} from 'axios';

interface BrandProductAddFormProps {
  selectedBrand: AdminBrandResponse | undefined;
  handleAddBrandSuccess: () => void;
  handleAddProductSuccess: () => void;
}

interface ProductFormProps extends Pick<BrandProductAddFormProps, 'selectedBrand' | 'handleAddProductSuccess'> {}

const ProductForm: React.FC<ProductFormProps> = ({selectedBrand, handleAddProductSuccess}) => {
  const [newProduct, setNewProduct] = useState<AdminProductAddRequest>({
    name: '',
    price: 0,
    categoryId: 1,
    brandId: 0,
    status: 'EXPOSED',
  });
  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const fetchCategories = useFetchCategories();
  const [error, setError] = useState<BadRequestErrorResponse | null>(null);

  useEffect(() => {
    const getCategories = async () => {
      const response = await fetchCategories({
        errorCallback: () => console.error('Failed to fetch categories'),
      });
      setCategories(response || []);
    };

    getCategories();
  }, []);

  const handleAddProduct = async () => {
    if (selectedBrand && selectedBrand.id > 0) {
      try {
        await addProductToBrand(selectedBrand.id, newProduct);
        setNewProduct({name: '', price: 0, categoryId: 1, brandId: 1, status: 'EXPOSED'});
        handleAddProductSuccess();
      } catch (err) {
        const axiosError = err as AxiosError;
        if (axiosError.response && axiosError.response.status === 400) {
          setError(axiosError.response.data as BadRequestErrorResponse);
        } else {
          alert(err);
        }
      }
    }
  };

  if (selectedBrand && selectedBrand.id > 0) {
    return (
      <>
        <h5 style={{marginBottom: '0px', paddingBottom: '0px'}}>✅ {selectedBrand.name}</h5>
        <ErrorBox error={error} />
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
  const [error, setError] = useState<BadRequestErrorResponse | null>(null);

  const handleAddBrand = async () => {
    try {
      await addBrand({name: newBrandName});
      setNewBrandName('');
      handleAddBrandSuccess();
    } catch (err) {
      const axiosError = err as AxiosError;
      if (axiosError.response && axiosError.response.status === 400) {
        setError(axiosError.response.data as BadRequestErrorResponse);
      } else {
        alert(err);
      }
    }
  };

  return (
    <>
      <ErrorBox error={error} />
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
      <ProductForm selectedBrand={selectedBrand} handleAddProductSuccess={handleAddProductSuccess} />
    </>
  );
};

export default BrandProductAddForm;
