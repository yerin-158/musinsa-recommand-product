import React, {useState, useEffect} from 'react';
import {AdminProductAddRequest, AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import '../../css/admin.css';
import {CategoryResponse} from '../../model/store/Category';
import useFetchCategories from '../../hooks/useFetchCategories';

interface ProductEditFormProps {
  selectedBrandId: number;
  product: AdminProductFullInfoResponse | null;
  onUpdateProduct: (updatedProduct: AdminProductAddRequest) => void;
  onDeleteProduct: () => void;
}

const ProductEditForm: React.FC<ProductEditFormProps> = ({
  selectedBrandId,
  product,
  onUpdateProduct,
  onDeleteProduct,
}) => {
  const [updatedProduct, setUpdatedProduct] = useState<AdminProductAddRequest>({
    name: '',
    price: 0,
    categoryId: 1,
    status: 'EXPOSED',
    brandId: selectedBrandId,
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
  }, []);

  useEffect(() => {
    if (product) {
      setUpdatedProduct({
        name: product.product.name,
        price: product.product.price,
        categoryId: product.product.categoryId,
        status: 'EXPOSED',
        brandId: selectedBrandId,
      });
    }
  }, [product, selectedBrandId]);

  const handleUpdate = () => {
    if (product) {
      onUpdateProduct(updatedProduct);
    }
  };

  const handleDelete = async () => {
    if (product) {
      onDeleteProduct();
    }
  };

  if (!product) return null;

  return (
    <>
      <h5 style={{marginBottom: '0px', paddingBottom: '0px'}}>상품 수정</h5>
      <div className="form-box">
        <div className="input-group">
          <div className="input-wrapper">
            <input
              type="text"
              value={updatedProduct.name}
              onChange={(e) => setUpdatedProduct({...updatedProduct, name: e.target.value})}
              placeholder="Product Name"
            />
          </div>
          <div className="input-wrapper">
            <input
              type="number"
              value={updatedProduct.price}
              onChange={(e) => setUpdatedProduct({...updatedProduct, price: +e.target.value})}
              placeholder="Product Price"
            />
          </div>
          <div className="input-wrapper">
            <select
              value={updatedProduct.categoryId}
              onChange={(e) => setUpdatedProduct({...updatedProduct, categoryId: +e.target.value})}>
              {categories.map(({id, name}) => (
                <option value={id} key={`category_${id}`}>
                  {name}
                </option>
              ))}
            </select>
          </div>
          <div className="button-wrapper">
            <button className="primary-button" onClick={handleUpdate}>
              상품 수정하기
            </button>
            <button onClick={handleDelete} style={{marginLeft: '10px', backgroundColor: 'red', color: 'white'}}>
              상품 삭제하기
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductEditForm;
