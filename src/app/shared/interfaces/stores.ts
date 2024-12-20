export interface Store {
  id: number;
  name: string;
  siteURL: string;
  logo: Image;
}
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

export interface StoreInsert {
  addStoreName: string;
  addStoreUrl: string;
  logoURL: string;
}
