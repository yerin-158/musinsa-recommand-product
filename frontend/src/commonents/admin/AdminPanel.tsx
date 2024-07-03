import React, {useState, useEffect} from 'react';
import {
  updateBrandStatus,
  getAllBrands,
  getBrandProducts,
  modifyBrandProduct,
  deleteBrandProduct,
} from '../../util/apiUtils';
import '../../css/admin.css';
import BrandList from './BrandList';
import BrandProducts from './BrandProducts';
import ProductEditForm from './ProductEditForm';
import {AdminBrandResponse} from '../../model/admin/AdminBrand';
import {AdminProductAddRequest, AdminProductFullInfoResponse} from '../../model/admin/AdminProduct';
import BrandProductAddForm from './BrandProductAddForm';
import useFetchBrand from '../../hooks/useFetchBrands';
import {BrandStatus} from '../../model/types';
import {AxiosError} from 'axios';
import {BadRequestErrorResponse} from '../../model/common/BadRequestErrorResponse';
import ErrorBox from '../common/ErrorBox';

const AdminPanel: React.FC = () => {
  const [brands, setBrands] = useState<AdminBrandResponse[]>([]);
  const [selectedBrand, setSelectedBrand] = useState<AdminBrandResponse>();
  const [products, setProducts] = useState<AdminProductFullInfoResponse[] | null>(null);
  const [selectedProduct, setSelectedProduct] = useState<AdminProductFullInfoResponse | null>(null);
  const [error, setError] = useState<BadRequestErrorResponse | null>(null);

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
      errorCallback: (err) => {
        const axiosError = err as AxiosError;
        if (axiosError.response && axiosError.response.status === 400) {
          setError(axiosError.response.data as BadRequestErrorResponse);
        } else {
          alert(err);
        }
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
          const axiosError = err as AxiosError;
          if (axiosError.response && axiosError.response.status === 400) {
            setError(axiosError.response.data as BadRequestErrorResponse);
          } else {
            alert(err);
          }
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
        setFetchNewProduct(true);
        setSelectedProduct(null);
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

  const handleDeleteProduct = async () => {
    if (selectedProduct && selectedBrand) {
      try {
        await deleteBrandProduct(selectedBrand.id, selectedProduct.product.id);
        setFetchNewProduct(true);
        setSelectedProduct(null);
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

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const handleChangeBrandStatus = async (status: BrandStatus) => {
    if (selectedBrand) {
      try {
        await updateBrandStatus(selectedBrand.id, status);
        handleFetchBrands();
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

  return (
    <div className="admin-content">
      <BrandProductAddForm
        selectedBrand={selectedBrand}
        handleAddBrandSuccess={() => setFetchNewBrand(true)}
        handleAddProductSuccess={() => setFetchNewProduct(true)}
      />
      <ErrorBox error={error} />
      <div className="admin-panel">
        <BrandList brands={brands} onBrandChange={handleBrandChange} />
        <BrandProducts
          products={products}
          onProductClick={handleProductClick}
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={handlePageChange}
          brandIsActive={selectedBrand?.status === 'EXPOSED'}
          changeBrandStatus={handleChangeBrandStatus}
        />
      </div>
      {selectedProduct && (
        <ProductEditForm
          selectedBrandId={selectedBrand?.id || 0}
          product={selectedProduct}
          onUpdateProduct={handleUpdateProduct}
          onDeleteProduct={handleDeleteProduct}
        />
      )}
    </div>
  );
};

export default AdminPanel;
