import {AdminBrandResponse} from './AdminBrand';
import {AdminCategoryResponse} from './AdminCategory';

export interface AdminProductAddRequest {
  name: string;
  price: number;
  brandId: number;
  categoryId: number;
}
export interface AdminProductResponse {
  id: number;
  name: string;
  price: number;
  brandId: number;
  categoryId: number;
  status: ProductStatus;
  createdAt: string;
  updatedAt: string;
}

export interface AdminProductModifyRequest {
  name?: string;
  price?: number;
}

export interface AdminProductStatusModifyRequest {
  status: ProductStatus;
}

export interface AdminProductFullInfoResponse {
  product: AdminProductResponse;
  brand: AdminBrandResponse;
  category: AdminCategoryResponse;
}

export type ProductStatus = 'DRAFT' | 'EXPOSED' | 'ADMIN_HIDDEN' | 'SOLD_OUT' | 'DELETED';
