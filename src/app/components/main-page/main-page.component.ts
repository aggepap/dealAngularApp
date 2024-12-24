import { Component, inject } from '@angular/core';
import { HeroComponent } from './hero/hero.component';
import { ProductsGridComponent } from '../products/products-grid/products-grid.component';
import { DealFiltersComponent } from './deal-filters/deal-filters.component';
import type { DealCategories } from '../../shared/interfaces/deal-categories';
import { CategoriesService } from '../../shared/services/categories.service';
import { ProductsService } from '../../shared/services/products.service';
import type { ImportedDeal } from '../../shared/interfaces/deals';
import type { filterSend } from '../../shared/interfaces/products';
import { StoresService } from '../../shared/services/stores.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ErrorMessageComponent } from '../../shared/components/error-message/error-message.component';
import { CategoriesSectionComponent } from './categories-grid/categories-section.component';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    HeroComponent,
    ProductsGridComponent,
    ReactiveFormsModule,
    DealFiltersComponent,
    CommonModule,
    FormsModule,
    ErrorMessageComponent,
    CategoriesSectionComponent,
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css',
})
export class MainPageComponent {
  //==============================================
  //  Services injection
  //==============================================
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);
  storesService = inject(StoresService);

  //==============================================
  //  Properties
  //==============================================
  categoriesList: DealCategories[] = [];
  dealsList: ImportedDeal[] = [];
  storesList: any = [];
  totalElements = 0;
  totalPages = 0;
  textFiltered = '';
  pagesNumber = 0;
  pageSize = 12;
  sortDirection = 'ASC';
  sortBy = 'name';
  StoreId = -1;
  errorMessage = '';

  //==============================================
  //  Boolean checks for loading and errors
  //==============================================
  isLoading = true;
  hasError = false;

  //==============================================
  //  Seach Filter
  //==============================================
  filter = {
    name: '',
    category: '',
    page: this.pagesNumber,
    size: this.pageSize,
  };

  ngOnInit() {
    this.getProductsFromSearch(this.filter);
    this.categoriesService.getCategories().subscribe(
      (data: DealCategories[]) => {
        this.categoriesList = data;
      },
      (error) => {
        this.hasError = true;
        this.errorMessage = 'Error fetching categories';
      }
    );

    this.storesService.getStores().subscribe({
      next: (data) => {
        this.storesList = data;
      },
      error: (error) => {
        this.hasError = true;
        this.errorMessage = 'Error fetching stores';
      },
    });
  }

  //==============================================
  //  Changes page number on pagination
  //==============================================
  onPageChange(newPage: number): void {
    if (newPage < 0 || newPage > this.totalPages) {
      return; // Prevent invalid page numbers
    }

    this.pagesNumber = newPage;
    this.getProductsFromSearch(this.filter);
  }
  //==============================================
  //  Changes page size on pagination and gets products
  //==============================================

  onPageSizeChange() {
    this.pagesNumber = 0;
    this.getProductsFromSearch(this.filter);
  }

  //Get products from search form
  //==============================================================================
  getProductsFromSearch(filters: filterSend) {
    this.productService
      .getStoreProductsPaginatedFiltered(
        filters,
        this.pagesNumber,
        this.pageSize,
        this.StoreId
      )
      .subscribe({
        next: (data: any) => {
          this.dealsList = data.data.map((deal: any) => ({
            id: deal.id,
            name: deal.name,
            description: deal.description,
            categoryName: deal.category?.name || '',
            categoryId: deal.category?.id || 0,
            storeId: deal.store?.id || 0,
            storeName: deal.store?.name || '',
            coupon: deal.coupon,
            url: deal.url,
            price: deal.price,
            image: deal.image,
            lowestPrice: deal.lowestPrice,
          }));

          this.totalPages = data.totalPages;
          this.pageSize = data.pageSize;
          this.totalElements = data.totalElements;
        },
        error: (error) => {
          this.isLoading = false;
          this.hasError = true;
          this.errorMessage = 'Error fetching Deals';
        },
        complete: () => {
          this.isLoading = false;
        },
      });
  }

  generateSearchFilter(filter: (string | number)[]): void {
    if (filter[0] !== null) {
      this.StoreId = Number(filter[0]);
    }
    this.filter.category = String(filter[1]);
    this.filter.name = String(filter[2]);
    this.pagesNumber = 0;
    this.getProductsFromSearch(this.filter);
  }
}
