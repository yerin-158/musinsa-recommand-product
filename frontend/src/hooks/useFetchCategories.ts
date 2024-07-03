import {CategoryResponse} from '../model/store/Category';
import {getAllCategories} from '../util/apiUtils';

export default function useFetchCategories() {
  const fetchCategories = async ({errorCallback}: {errorCallback: () => void}) => {
    try {
      const categoriesResponse: CategoryResponse[] = await getAllCategories();
      return categoriesResponse || [];
    } catch (err) {
      errorCallback();
    }
  };

  return fetchCategories;
}
