import type { DealCategories } from "./deal-categories";
import type { Stores } from "./stores";

export interface Deals {
  id:number,
  name:string,
  store: string,
  category:string,
  coupon:string,
  url: string,
  price:number,
  img:string,
  createdAt: number,
  updatedAt:number,
  lowestPrice?:number,
  priceHistory?: [Date, number][];
}
