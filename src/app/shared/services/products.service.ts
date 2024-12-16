import { environment } from '@/src/environments/environment.development';
import { inject, Injectable } from '@angular/core';
import type { filterSend } from '../interfaces/products';
import { HttpClient, HttpParams } from '@angular/common/http';

import { ImportedDeal } from '../interfaces/deals';

const apiUrl = environment.apiURL;
const PRODUCTS_API_URL = `${apiUrl}/products`;

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  http: HttpClient = inject(HttpClient);

  // Get latest Store products
  //==============================================================================
  getLatestProducts() {
    return this.http.get<ImportedDeal[]>(`${PRODUCTS_API_URL}/all`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  // Add a new product
  addProduct(formData: FormData) {
    return this.http.post(`${PRODUCTS_API_URL}/add`, formData).subscribe({
      next: (response) => {
        console.log('Upload successful:', response);
      },
      error: (error) => {
        console.error('Upload failed:', error);
      },
    });
  }

  //Get products paginated and filtered by name and category
  //==============================================================================
  getProductsPaginatedFiltered(filter: filterSend, page: number, size: number) {
    const params = new HttpParams()
      .set('name', filter.name)
      .set('categoryId', filter.category)
      .set('pageSize', size)
      .set('page', page);

    return this.http.get(`${PRODUCTS_API_URL}/results`, { params });
  }

  //Get products paginated and filtered by name and category
  //==============================================================================
  getStoreProductsPaginatedFiltered(
    filter: filterSend,
    page: number,
    size: number,
    storeId: number
  ) {
    const params = new HttpParams()
      .set('name', filter.name)
      .set('categoryId', filter.category)
      .set('storeId', storeId)
      .set('pageSize', size)
      .set('page', page);

    return this.http.get(`${PRODUCTS_API_URL}/results`, { params });
  }

  getProductById(id: number) {
    return this.http.get<ImportedDeal>(`${PRODUCTS_API_URL}/find?id=${id}`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }
}
