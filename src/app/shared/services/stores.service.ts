import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type { Store } from '../interfaces/stores';
import { environment } from '@/src/environments/environment.development';

const apiUrl = environment.apiURL;
const STORES_API_URL = `${apiUrl}/stores`;

@Injectable({
  providedIn: 'root',
})
export class StoresService {
  //======================================================
  //  Inject Services
  //======================================================
  http: HttpClient = inject(HttpClient);

  /**
   * Retrieves a list of all stores from the backend API.
   *
   * @returns Observable of an array of `Store` objects.
   */
  getStores() {
    return this.http.get<Store[]>(STORES_API_URL, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  /**
   * Retrieves a specific store by its ID.
   *
   * @param id The ID of the store to retrieve.
   * @returns Observable of a single `Store` object.
   */
  getStoreById(id: number) {
    return this.http.get<Store>(`${STORES_API_URL}/find?id=${id}`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  /**
   * Creates a new store on the backend API using FormData.
   *
   * @param formData An object containing store data in FormData format.
   * @returns Observable of the newly created `Store` object.
   */
  addStore(formData: FormData) {
    console.log(formData);
    return this.http.post<Store>(`${STORES_API_URL}/add`, formData);
  }

  /**
   * Updates an existing store on the backend API using FormData.
   *
   * @param storeId The ID of the store to update.
   * @param formData An object containing updated store data in FormData format.
   * @returns Observable of the updated `Store` object.
   */
  updateStore(storeId: number, formData: FormData) {
    return this.http.put<Store>(
      `${STORES_API_URL}/update/${storeId}`,
      formData
    );
  }

  /**
   * Deletes a store by its ID.
   *
   * @param id The ID of the store to delete.
   */
  deleteStore(id: number) {
    let storeName = '';
    this.getStoreById(id).subscribe({
      next: (data: Store) => {
        storeName = data.name;
      },
      error: (error) => console.error('Error fetching Store', error),
      complete: () => console.log('Store info fetched'),
    });
    const deleteConfirmed = confirm(
      `Are you sure you want to delete Store ${storeName}`
    );
    if (deleteConfirmed) {
      this.http.delete(`${STORES_API_URL}/remove?id=${id}`).subscribe({
        next: () => {
          alert(`Store ${storeName} was deleted succesfully`);
          window.location.reload();
        },
        error: (error) => {
          if (storeName === 'Other') {
            alert(
              'You cannot delete the "Other" Store. This is the default Store for products that do not have a Store.'
            );
            return;
          }
          alert('Failed to delete the Store. Please try again.');
        },
        complete: () => console.log('ok'),
      });
    } else {
      alert('Deletion canceled');
    }
  }
}
