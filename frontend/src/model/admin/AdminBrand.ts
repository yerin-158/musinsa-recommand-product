import {ResponseBase} from '../common/Base';
import {BrandStatus} from '../types';

export interface AdminBrandAddRequest {
  name: string;
}

export interface AdminBrandResponse extends ResponseBase {
  id: number;
  name: string;
  status: BrandStatus;
}

export interface AdminBrandStatusModifyRequest {
  status: BrandStatus;
}
