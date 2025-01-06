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
