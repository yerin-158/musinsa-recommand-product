import {ProductStatus} from '../model/types';

export const convertProductStatus = (status: ProductStatus) => {
  switch (status) {
    case 'DRAFT':
      return '임시저장';
    case 'EXPOSED':
      return '노출 중';
    case 'ADMIN_HIDDEN':
      return '비노출';
    case 'SOLD_OUT':
      return '품절';
    case 'DELETED':
      return '삭제됨';
  }
};
