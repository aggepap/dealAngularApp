import type { DealCategories } from "./deal-categories";
import type { Stores } from "./stores";

export interface Deals {
  name:string,
  store: Stores,
  category:DealCategories,
  coupon:string,
  url: string,
  price:number,
  img:string,
  createdAt: Date,
  updatedAt:Date,
  lowestPrice:number,
  priceHistory: [Date, number][];
}
