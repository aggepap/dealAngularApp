import { environment } from '@/src/environments/environment.development';
import { inject, Injectable } from '@angular/core';
import type {
  productAddData,
  productFilteredPaginated,
} from '../interfaces/products';
import { HttpClient, HttpParams } from '@angular/common/http';

const apiUrl = environment.apiURL;
const PRODUCTS_API_URL = `${apiUrl}/products`;
let page = 0;
let size = 10;

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  http: HttpClient = inject(HttpClient);

  // Get all products paginated

  getAllProductsPaginated() {
    return this.http.get(`${PRODUCTS_API_URL}?/page=${page}&size=${size}`);
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
  getProductsPaginatedFiltered(filter: productFilteredPaginated) {
    const params = new HttpParams()
      .set('page', filter.page)
      .set('pageSize', filter.pageSize)
      .set('sortDirection', filter.sortDirection)
      .set('sortBy', filter.sortBy)
      .set('name', filter.name || '')
      .set('pageSize', filter.pageSize)
      .set('categoryId', filter.category?.id || '')
      .set('categoryId', filter.category?.name || '');

    const body = {
      page: filter.page,
      pageSize: filter.pageSize,
      sortDirection: filter.sortDirection,
      sortBy: filter.sortBy,
      name: filter.name,
      category: {
        id: filter.category.id,
        name: filter.category.name,
      },
    };
    return this.http.get(`${PRODUCTS_API_URL}/results`, { params });
  }
}
