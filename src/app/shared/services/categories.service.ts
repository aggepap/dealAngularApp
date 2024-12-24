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

  /**
   * Retrieves a list of all deal categories from the backend API.
   *
   * @returns Observable of an array of `DealCategories` objects.
   */
  getCategories() {
    return this.http.get<DealCategories[]>(CATEGORIES_API_URL, {
      headers: {
        Accept: 'application/json',
      },
    });
  }

  /**
   * Retrieves a specific deal category by its ID.
   *
   * @param id The ID of the category to retrieve. (number)
   *
   * @returns Observable of a single `DealCategories` object.
   */
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

  /**
   * Creates a new deal category on the backend API.
   *
   * @param icon The Font Awesome icon class name for the category. (string)
   * @param name The name of the category. (string)
   *
   * @returns Observable of a newly created `newCategory` object.
   */
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

  /**
   * Deletes a deal category by its ID.
   *
   * @param id The ID of the category to delete. (number)
   */
  deleteCategory(id: number) {
    let categoryName = '';
    this.getCategoryById(id).subscribe({
      next: (data: DealCategories) => {
        categoryName = data.name;
      },
      error: (error) => {
        console.error('Error fetching category', error);
      },
    });
    const deleteConfirmed = confirm(
      `Are you sure you want to delete category ${categoryName}`
    );
    if (deleteConfirmed) {
      this.http.delete(`${CATEGORIES_API_URL}/remove?id=${id}`).subscribe({
        next: () => {
          alert(`Category ${categoryName} was deleted succesfully`);
          window.location.reload();
        },
        error: (error) => {
          console.error('Error deleting category', error);
          if (categoryName === 'Other') {
            alert(
              'You cannot delete the "Other" category. This is the default Category for deals that do not belong to any other category.'
            );
            return;
          }
          alert('Failed to delete the category. Please try again.');
        },
      });
    } else {
      alert('Deletion canceled');
    }
  }

  /**
   * Updates an existing deal category on the backend API.
   * @param id The ID of the category to update.
   * @param icon The Font Awesome icon class name for the category.
   * @param name The name of the category.
   * @returns Observable of the updated `DealCategories` object.
   */
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
