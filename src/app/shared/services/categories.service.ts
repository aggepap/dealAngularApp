import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import type {
  DealCategories,
  newCategory,
} from '../interfaces/deal-categories';
import { environment } from '@/src/environments/environment.development';

const apiUrl = environment.apiURL;
const CATEGORIES_API_URL = `${apiUrl}/category`;

export const fontAwIcons = [
  'fa-star',
  'fa-mobile-screen',
  'fa-laptop',
  'fa-tablet-screen-button',
  'fa-tv',
  'fa-gift',
  'fa-shirt',
  'fa-futbol',
  'fa-camera',
  'fa-film',
  'fa-video',
  'fa-headphones',
  'fa-gamepad',
  'fa-mouse',
  'fa-wrench',
  'fa-gear',
  'fa-truck',
  'fa-clock',
  'fa-book',
  'fa-house',
  'fa-pencil',
  'fa-cube',
  'fa-car',
  'fa-bicycle',
  'fa-person-biking',
];

@Injectable({
  providedIn: 'root',
})
export class CategoriesService {
  http: HttpClient = inject(HttpClient);

  getCategories() {
    return this.http.get<DealCategories[]>(CATEGORIES_API_URL, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  getCategoryById(id: number) {
    return this.http.get<DealCategories>(
      `${CATEGORIES_API_URL}/find?id=${id}`,
      {
        headers: {
          Accept: 'application/json',
        },
      }
    );
  }

  addCategory(icon: string, name: string) {
    const body = {
      icon: icon,
      name: name,
    };
    return this.http.post<newCategory>(`${CATEGORIES_API_URL}/add`, body, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    });
  }

  deleteCategory(id: number) {
    let categoryName = '';
    this.getCategoryById(id).subscribe(
      (data: DealCategories) => {
        categoryName = data.name;
      },
      (error) => console.error('Error fetching category', error)
    );
    const deleteConfirmed = confirm(
      `Are you sure you want to delete category ${categoryName}`
    );
    if (deleteConfirmed) {
      this.http.delete(`${CATEGORIES_API_URL}/remove?id=${id}`).subscribe(
        () => {
          alert(`Category ${categoryName} was deleted succesfully`);
          window.location.reload();
        },
        (error) => {
          console.error('Error deleting category', error);
          alert('Failed to delete the category. Please try again.');
        }
      );
    } else {
      alert('Deletion canceled');
    }
  }

  updateCategory(id: number, icon: string, name: string) {
    const body = {
      id: id,
      icon: icon,
      name: name,
    };

    return this.http.patch<DealCategories>(
      `${CATEGORIES_API_URL}/update`,
      body,
      {
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      }
    );
  }
}
