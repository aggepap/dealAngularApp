export interface ProductUpdate {
  updateProductCategory: number;
  updateProductName: string;
  updateProductDescription: string;
  updateProductImage: string;
  updateDealStore: number;
  updateDealURL: string;
  updateDealCoupon: string;
  updateDealPrice: number;
}
export interface productFilteredPaginated {
  page: number;
  pageSize: number;
  sortDirection: string;
  sortBy: string;
  name: string | '';
  category: {
    id: number | undefined;
  };
}

export interface filterSend {
  name: string;
  category: string;
}

// Single Product Interface
interface Image {
  createdAt: string;
  updatedAt: string;
  id: number;
  filename: string;
  savedName: string;
  filePath: string;
  contentType: string;
  extension: string;
}

interface Product {
  createdAt: string;
  updatedAt: string;
  id: number;
  name: string;
  description: string;
  coupon: string;
  price: number;
  lowestPrice: null;
  image: Image;
  url: string;
}

interface Category {
  id: number;
  name: string;
  icon: string;
  products: Product[];
  allCategoryProducts: Product[];
}

interface Store {
  id: number;
  name: string;
  siteURL: string;
  logoURL: string;
  products: Product[];
  allStoreProducts: Product[];
}

export interface ProductData {
  id: number;
  name: string;
  description: string;
  category: Category;
  store: Store;
  coupon: string;
  url: string;
  price: number;
  image: Image;
  lowestPrice: null;
}
