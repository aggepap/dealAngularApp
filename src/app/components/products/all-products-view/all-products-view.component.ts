import { Component, type ElementRef, inject, ViewChild } from '@angular/core';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import type { productFilteredPaginated } from '@/src/app/shared/interfaces/products';
import { ProductsGridComponent } from '../products-grid/products-grid.component';

@Component({
  selector: 'app-all-products-view',
  standalone: true,
  imports: [ProductsGridComponent],
  templateUrl: './all-products-view.component.html',
  styleUrl: './all-products-view.component.css',
})
export class AllProductsViewComponent {
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);
  categoriesList: DealCategories[] = [];
  totalElements = 0;
  totalPages = 0;

  textFiltered = '';
  pagesNumber = 0;
  pageSize = 10;
  sortDirection = 'ASC';
  sortBy = 'name';
  name = '';

  @ViewChild('dropdownButton', { static: false }) dropdownButton!: ElementRef;
  @ViewChild('dropdownMainButton', { static: false })
  dropdownMainButton!: ElementRef;
  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;

  ngOnInit() {
    const filter = {
      page: this.pagesNumber,
      pageSize: this.pageSize,
      sortDirection: this.sortDirection,
      sortBy: this.sortBy,
      name: this.name,
      category: {
        id: undefined,
        name: undefined,
      },
    };
    console.log(filter);

    this.getProductsFromSearch(filter);

    this.categoriesService.getCategories().subscribe(
      (data: DealCategories[]) => {
        this.categoriesList = data;
      },
      (error) => console.error('Error fetching categories', error)
    );
  }

  decreasePage() {
    if (this.pagesNumber > 1) {
      this.pagesNumber--;
    }
    this.pagesNumber = 0;
    console.log(this.pagesNumber);
  }
  increasePage() {
    if (this.pagesNumber < this.totalPages) {
      this.pagesNumber++;
    }
  }

  pickCategoryFromDropdown(categoryName: DealCategories) {
    const mainButton: HTMLElement = this.dropdownMainButton.nativeElement;
    mainButton.innerText = categoryName.name;
  }

  getProductsFromSearch(filters: productFilteredPaginated) {
    this.productService.getProductsPaginatedFiltered(filters).subscribe({
      next: (response: any) => {
        console.log(response);

        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
        console.log(this.totalPages);
      },
      error: (error) => {
        console.log('Could not fetch products', error);
      },
      complete: () => {},
    });
  }
}
