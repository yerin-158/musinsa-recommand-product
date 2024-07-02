import {ResponseBase} from '../common/Base';
import {BrandStatus} from '../types';

export interface AdminBrandAddRequest {
  name: string;
}

//Long id, String name, BrandStatus status, LocalDateTime createdAt, LocalDateTime updatedAt
export interface AdminBrandResponse extends ResponseBase {
  id: number;
  name: string;
  status: BrandStatus;
}
