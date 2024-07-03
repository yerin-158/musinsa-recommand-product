import {ResponseBase} from '../common/Base';
import {ProductStatus} from '../types';
import {AdminBrandResponse} from './AdminBrand';
import {AdminCategoryResponse} from './AdminCategory';

export interface AdminProductAddRequest {
  name: string;
  price: number;
  brandId: number;
  categoryId: number;
  status: ProductStatus;
}
export interface AdminProductResponse extends ResponseBase {
  id: number;
  name: string;
  price: number;
  brandId: number;
  categoryId: number;
  status: ProductStatus;
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
