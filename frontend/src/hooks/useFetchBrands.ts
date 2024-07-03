import {getAllBrands} from '../util/apiUtils';
import {AdminBrandResponse} from '../model/admin/AdminBrand';

export default function useFetchBrand() {
  const fetchBrands = async ({
    callback,
    errorCallback,
  }: {
    callback: (brandsResponse: AdminBrandResponse[]) => void;
    errorCallback: (err: any) => void;
  }) => {
    try {
      const brandsResponse = await getAllBrands();
      callback(brandsResponse);
    } catch (err) {
      errorCallback(err);
    }
  };

  return fetchBrands;
}
