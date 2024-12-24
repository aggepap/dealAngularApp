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

  /**
   * Retrieves a list of the latest products from the backend API.
   *
   * @returns Observable of an array of `ImportedDeal` objects.
   */
  getLatestProducts() {
    return this.http.get<ImportedDeal[]>(`${PRODUCTS_API_URL}/all`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  // Add a new product
  //==============================================================================

  /**
   * Creates a new product on the backend API using FormData.
   *
   * @param formData An object containing product data in FormData format.
   *
   * @returns Subscription object (for advanced error handling if needed).
   */
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

  /**
   * Updates an existing product on the backend API using FormData.
   *
   * @param productId The ID of the product to update.
   * @param formData An object containing updated product data in FormData format.
   *
   * @returns Subscription object (for advanced error handling if needed).
   */
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
            location.reload();
          }, 500);
        },
      });
  }

  //Get products paginated and filtered by name and category
  //==============================================================================

  /**
   * Retrieves a paginated and filtered list of products from the backend API.
   *
   * @param filter An object containing filtering criteria (name and category).
   * @param page The current page number for pagination.
   * @param size The number of products to retrieve per page.
   *
   * @returns Observable of an array of `ProductData` objects.
   */
  getProductsPaginatedFiltered(filter: filterSend, page: number, size: number) {
    const params = new HttpParams()
      .set('name', filter.name)
      .set('categoryId', filter.category)
      .set('pageSize', size)
      .set('page', page);

    return this.http.get(`${PRODUCTS_API_URL}/results`, { params });
  }

  /**
   * Retrieves a paginated and filtered list of products from a specific store.
   * @param filter An object containing filtering criteria (name and category).
   * @param page The current page number for pagination.
   * @param size The number of products to retrieve per page.
   * @param storeId The ID of the store to filter products by.
   * @returns Observable of an array of `ProductData` objects.
   */
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

  /**
   * Retrieves a specific product by its ID.
   * @param id The ID of the product to retrieve.
   * @returns Observable of a single `ProductData` object.
   */
  getProductById(id: number) {
    return this.http.get<ProductData>(`${PRODUCTS_API_URL}/find?id=${id}`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  /**
   * Deletes a product by its ID.
   * @param id The ID of the product to delete.
   */
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
