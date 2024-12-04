export interface Store {
  id: string;
  name: string;
  siteURL: string;
  logoURL: string;
}

export interface StoreInsert {
  addStoreName: string;
  addStoreUrl: string;
  logoURL: string;
}
