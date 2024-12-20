import { environment } from '@/src/environments/environment.development';
import { inject, Injectable } from '@angular/core';
import type { filterSend, ProductData } from '../interfaces/products';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import type { ImportedDeal } from '../interfaces/deals';

const apiUrl = environment.apiURL;
const PRODUCTS_API_URL = `${apiUrl}/products`;

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  location = inject(Location);
  router = inject(Router);
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
        console.log('Product addition was successful:', response);
      },
      error: (error) => {
        console.error('Product addition  failed:', error);
      },
    });
  }

  updateProduct(productId: number, formData: FormData) {
    return this.http
      .put(`${PRODUCTS_API_URL}/update/${productId}`, formData)
      .subscribe({
        next: (response) => {
          console.log('Product update was successful:', response);
        },
        error: (error) => {
          console.error('Product update failed:', error);
        },
        complete: () => {
          console.log('Product update completed.');
          setTimeout(() => {
            // location.reload();
          }, 1000);
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
      .set('pageSize', size)
      .set('page', page)
      .set('storeId', storeId);

    console.log(params);

    return this.http.get(`${PRODUCTS_API_URL}/results`, { params });
  }

  getProductById(id: number) {
    return this.http.get<ProductData>(`${PRODUCTS_API_URL}/find?id=${id}`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  deleteProduct(id: number) {
    let productName = '';
    this.getProductById(id).subscribe({
      next: (data: ProductData) => {
        console.log(data);

        productName = data.name;
      },
      error: (error) => console.error('Error fetching product', error),
      complete: () => console.log('product info fetched'),
    });
    const deleteConfirmed = confirm(
      `Are you sure you want to delete product ${productName}`
    );
    if (deleteConfirmed) {
      this.http.delete(`${PRODUCTS_API_URL}/remove/${id}`).subscribe({
        next: () => {
          alert(`Product ${productName} was deleted succesfully`);
          this.location.back();
        },
        error: (error) => {
          console.error('Error deleting Product', error);
          alert('Failed to delete this product. Please try again.');
        },
        complete: () => console.log('ok'),
      });
    } else {
      alert('Deletion canceled');
    }
  }
}
