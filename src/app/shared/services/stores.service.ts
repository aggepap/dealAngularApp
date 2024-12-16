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
  http: HttpClient = inject(HttpClient);

  getStores() {
    return this.http.get<Store[]>(STORES_API_URL, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  getStoreById(id: number) {
    return this.http.get<Store>(`${STORES_API_URL}/find?id=${id}`, {
      headers: {
        Accept: 'application/json',
      },
    });
  }
  addStore(name: string, siteURL: string, logoURL: string) {
    const body = {
      name: name,
      siteURL: siteURL,
      logoURL: logoURL,
    };
    console.log(body);

    return this.http.post<Store>(`${STORES_API_URL}/add`, body, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    });
  }
  updateStore(id: number, name: string, siteURL: string, logoURL: string) {
    const body = {
      id: id,
      name: name,
      siteURL: siteURL,
      logoURL: logoURL,
    };

    return this.http.patch<Store>(`${STORES_API_URL}/update`, body, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    });
  }

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
          console.error('Error deleting Store', error);
          alert('Failed to delete the Store. Please try again.');
        },
        complete: () => console.log('ok'),
      });
    } else {
      alert('Deletion canceled');
    }
  }
}
