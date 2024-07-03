import React, {useState, useEffect} from 'react';
import {activateBrand, getAllBrands, getBrandProducts, modifyBrandProduct} from '../../util/apiUtils';
import '../../css/admin.css';
import BrandList from './BrandList';
import BrandProducts from './BrandProducts';
import ProductEditForm from './ProductEditForm';
import {AdminBrandResponse} from '../../model/admin/AdminBrand';
import {AdminProductAddRequest, AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import BrandProductAddForm from './BrandProductAddForm';
import useFetchBrand from '../../hooks/useFetchBrands';

const AdminPanel: React.FC = () => {
  const [brands, setBrands] = useState<AdminBrandResponse[]>([]);
  const [selectedBrand, setSelectedBrand] = useState<AdminBrandResponse>();
  const [products, setProducts] = useState<AdminProductFullInfoResponse[] | null>(null);
  const [selectedProduct, setSelectedProduct] = useState<AdminProductFullInfoResponse | null>(null);
  const [error, setError] = useState<string | null>(null);

  const [fetchNewBrand, setFetchNewBrand] = useState(true);
  const [fetchNewProduct, setFetchNewProduct] = useState(true);

  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchBrands = useFetchBrand();
  const handleFetchBrands = () => {
    fetchBrands({
      callback: (brandsResponse) => {
        setBrands(brandsResponse);
        setFetchNewBrand(false);
      },
      errorCallback: () => {
        setError('Failed to fetch brands');
      },
    });
  };

  useEffect(() => {
    if (fetchNewBrand) {
      handleFetchBrands();
    }
  }, [fetchNewBrand]);

  useEffect(() => {
    const fetchProducts = async () => {
      if (selectedBrand && selectedBrand.id > 0) {
        try {
          const productsResponse = await getBrandProducts(selectedBrand.id, currentPage);
          setProducts(productsResponse.content);
          setFetchNewProduct(false);
          setTotalPages(productsResponse.totalPages);
        } catch (err) {
          setError('Failed to fetch products');
        }
      }
    };

    fetchProducts();
  }, [selectedBrand, fetchNewProduct, currentPage]);

  const handleBrandChange = (brand: AdminBrandResponse) => {
    setSelectedBrand(brand);
    setSelectedProduct(null);
    setCurrentPage(0);
  };

  const handleProductClick = (product: AdminProductFullInfoResponse) => {
    setSelectedProduct(product);
  };

  const handleUpdateProduct = async (updatedProduct: AdminProductAddRequest) => {
    if (selectedProduct && selectedBrand) {
      try {
        await modifyBrandProduct(selectedBrand.id, selectedProduct.product.id, updatedProduct);
        const updatedProducts = await getBrandProducts(selectedBrand.id);
        setProducts(updatedProducts.content);
        setSelectedProduct(null);
      } catch (err) {
        setError('Failed to update product');
      }
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const handleActivateBrand = async () => {
    if (selectedBrand) {
      try {
        await activateBrand(selectedBrand.id);
        handleFetchBrands();
      } catch (err) {
        setError('Failed to activate brand');
      }
    }
  };

  if (error) {
    return <h5>에러가 발생했습니다. {error}</h5>;
  }

  return (
    <div className="admin-content">
      <BrandProductAddForm
        selectedBrand={selectedBrand}
        handleAddBrandSuccess={() => setFetchNewBrand(true)}
        handleAddProductSuccess={() => setFetchNewProduct(true)}
      />
      <div className="admin-panel">
        <BrandList brands={brands} onBrandChange={handleBrandChange} />
        <BrandProducts
          products={products}
          onProductClick={handleProductClick}
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={handlePageChange}
          brandIsActive={selectedBrand?.status === 'EXPOSED'}
          onActivateBrand={handleActivateBrand}
        />
      </div>
      {selectedProduct && (
        <ProductEditForm
          selectedBrandId={selectedBrand?.id || 0}
          product={selectedProduct}
          onUpdateProduct={handleUpdateProduct}
        />
      )}
    </div>
  );
};

export default AdminPanel;
