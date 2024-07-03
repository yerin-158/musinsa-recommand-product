import {BrandStatus} from '../types';

export interface BrandSimpleResponse {
  id: number;
  name: string;
  status: BrandStatus;
}

export interface CategorySimpleResponse {
  id: number;
  name: string;
}

export interface ProductSimpleResponse {
  id: number;
  name: string;
  price: number;
}

export interface PageResponse<T> {
  totalPages: number;
  totalElements: number;
  size: number;
  page: number;
  isFirst: boolean;
  isLast: boolean;
  isEmpty: boolean;
  content: T[];
}

export interface ProductByCategoryResponse {
  category: CategorySimpleResponse;
  lowestPriceProducts?: ProductResponse[];
  highestPriceProducts?: ProductResponse[];
}

export interface ProductResponse {
  category: CategorySimpleResponse;
  brand: BrandSimpleResponse;
  product: ProductSimpleResponse;
}

export interface ProductSetResponse {
  products: ProductResponse[];
  sumPrice: number;
}
