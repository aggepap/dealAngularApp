import type { DealCategories } from './deal-categories';

export interface Deals {
  id: number;
  name: string;
  storeId: number;
  categoryId: number;
  coupon: string;
  url: string;
  price: number;
  img: string;
  createdAt: Date;
  updatedAt: Date;
  lowestPrice?: number;
  priceHistory?: [Date, number][];
}
export interface Image {
  createdAt: string;
  updatedAt: string;
  id: number;
  filename: string;
  savedName: string;
  filePath: string;
  contentType: string;
  extension: string;
}

export interface ImportedDeal {
  id: number;
  name: string;
  description: string;
  categoryName: string;
  categoryId: number;
  storeId: number;
  storeName: string;
  coupon: string | null;
  url: string;
  price: number;
  image: Image;
  lowestPrice: number | null;
}

export interface requestData {
  data: ImportedDeal;
  totalPages: number;
  totalElements: number;
  currentPage: number;
  pageSize: number;
}
