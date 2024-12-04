import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import type {
  DealCategories,
  newCategory,
} from '../interfaces/deal-categories';

const CATEGORIES_API_URL = 'http://localhost:8080/category';
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

  getCategoryById(id: string) {
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

  deleteCategory(id: string) {
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

  updateCategory(id: string, icon: string, name: string) {
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
