import axios from 'axios';
import {AdminBrandAddRequest, AdminBrandResponse} from '../model/admin/AdminBrand';
import {
  AdminProductAddRequest,
  AdminProductFullInfoResponse,
  AdminProductModifyRequest,
  AdminProductResponse,
  AdminProductStatusModifyRequest,
} from '../model/admin/AdminProduct';
import {ProductByCategoryResponse, ProductSetResponse} from '../model/store/Product';
import {PageResponse} from '../model/ResponseWrapper';
import {PriceType} from '../model/types';

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// AdminBrandApi
export const addBrand = async (request: AdminBrandAddRequest): Promise<AdminBrandResponse> => {
  const response = await apiClient.post<AdminBrandResponse>('/admin/api/v1/brands', request);
  return response.data;
};

export const getBrand = async (brandId: number): Promise<AdminBrandResponse> => {
  const response = await apiClient.get<AdminBrandResponse>(`/admin/api/v1/brands/${brandId}`);
  return response.data;
};

export const getAllBrands = async (): Promise<AdminBrandResponse[]> => {
  const response = await apiClient.get<AdminBrandResponse[]>('/admin/api/v1/brands');
  return response.data;
};

export const addProductToBrand = async (
  brandId: number,
  request: AdminProductAddRequest
): Promise<AdminProductFullInfoResponse> => {
  const response = await apiClient.post<AdminProductFullInfoResponse>(
    `/admin/api/v1/brands/${brandId}/products`,
    request
  );
  return response.data;
};

export const getBrandProduct = async (brandId: number, productId: number): Promise<AdminProductFullInfoResponse> => {
  const response = await apiClient.get<AdminProductFullInfoResponse>(
    `/admin/api/v1/brands/${brandId}/products/${productId}`
  );
  return response.data;
};

export const getBrandProducts = async (
  brandId: number,
  page: number = 0,
  size: number = 20
): Promise<PageResponse<AdminProductFullInfoResponse>> => {
  const response = await apiClient.get<PageResponse<AdminProductFullInfoResponse>>(
    `/admin/api/v1/brands/${brandId}/products`,
    {
      params: {page, size},
    }
  );
  return response.data;
};

export const modifyBrandProduct = async (
  brandId: number,
  productId: number,
  request: AdminProductModifyRequest
): Promise<AdminProductFullInfoResponse> => {
  const response = await apiClient.put<AdminProductFullInfoResponse>(
    `/admin/api/v1/brands/${brandId}/products/${productId}`,
    request
  );
  return response.data;
};

export const modifyBrandProductStatus = async (
  brandId: number,
  productId: number,
  request: AdminProductStatusModifyRequest
): Promise<AdminProductFullInfoResponse> => {
  const response = await apiClient.put<AdminProductFullInfoResponse>(
    `/admin/api/v1/brands/${brandId}/products/${productId}/status`,
    request
  );
  return response.data;
};

export const deleteBrandProduct = async (brandId: number, productId: number): Promise<AdminProductFullInfoResponse> => {
  const response = await apiClient.delete<AdminProductFullInfoResponse>(
    `/admin/api/v1/brands/${brandId}/products/${productId}`
  );
  return response.data;
};

// ProductApi
export const getProductsSummaryByCategory = async (
  categoryId: number,
  priceTypes: PriceType[],
  size: number = 1
): Promise<ProductByCategoryResponse> => {
  const response = await apiClient.get<ProductByCategoryResponse>('/products/summary', {
    params: {categoryId, priceType: priceTypes, size},
  });
  return response.data;
};

// RecommendApi
export const getLowestPriceProductSet = async (
  byBrand: boolean = false,
  brandId?: number
): Promise<ProductSetResponse> => {
  const response = await apiClient.get<ProductSetResponse>('/recommend/products', {
    params: {byBrand, brandId},
  });
  return response.data;
};
