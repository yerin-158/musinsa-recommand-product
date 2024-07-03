import React, {useState, useEffect} from 'react';
import {AdminProductAddRequest, AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import '../../css/admin.css';

interface ProductEditFormProps {
  selectedBrandId: number;
  product: AdminProductFullInfoResponse | null;
  onUpdateProduct: (updatedProduct: AdminProductAddRequest) => void;
}

const ProductEditForm: React.FC<ProductEditFormProps> = ({selectedBrandId, product, onUpdateProduct}) => {
  const [updatedProduct, setUpdatedProduct] = useState<AdminProductAddRequest>({
    name: '',
    price: 0,
    categoryId: 1,
    status: 'EXPOSED',
    brandId: selectedBrandId,
  });

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
              <option value={1}>상의</option>
              <option value={2}>아우터</option>
              <option value={3}>바지</option>
              <option value={4}>스니커즈</option>
              <option value={5}>가방</option>
              <option value={6}>모자</option>
              <option value={7}>양말</option>
              <option value={8}>악세서리</option>
            </select>
          </div>
          <div className="button-wrapper">
            <button className="primary-button" onClick={handleUpdate}>
              상품 수정하기
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductEditForm;
