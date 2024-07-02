import React from 'react';
import {AdminProductAddRequest, AdminProductFullInfoResponse} from '../model/admin/AdminProduct';

interface BrandProductsProps {
  products: AdminProductFullInfoResponse[] | null;
  onAddProduct: () => void;
  newProduct: AdminProductAddRequest;
  setNewProduct: (product: AdminProductAddRequest) => void;
}

const BrandProducts: React.FC<BrandProductsProps> = ({products, onAddProduct, newProduct, setNewProduct}) => {
  return (
    <div>
      <h2>Products</h2>
      <ul>
        {products?.map((product) => (
          <li key={product.product.id}>
            {product.product.name} - ${product.product.price}
          </li>
        ))}
      </ul>
      <h3>상품 추가</h3>
      <input
        type="text"
        value={newProduct.name}
        onChange={(e) => setNewProduct({...newProduct, name: e.target.value})}
        placeholder="Product Name"
      />
      <input
        type="number"
        value={newProduct.price}
        onChange={(e) => setNewProduct({...newProduct, price: +e.target.value})}
        placeholder="Product Price"
      />
      <select
        value={newProduct.categoryId}
        onChange={(e) => setNewProduct({...newProduct, categoryId: +e.target.value})}>
        <option value="">Select Category</option>
        {/* Populate categories here */}
      </select>
      <button onClick={onAddProduct}>상품 추가하기</button>
    </div>
  );
};

export default BrandProducts;
