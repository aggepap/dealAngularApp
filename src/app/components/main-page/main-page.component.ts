import { Component, inject } from '@angular/core';
import { DealCardsComponent } from '../deal-cards/deal-cards.component';
import { CategoriesSectionComponent } from './categories-grid/categories-section.component';
import { HeroComponent } from './hero/hero.component';
import { RouterLink } from '@angular/router';
import { ProductsGridComponent } from '../products/products-grid/products-grid.component';
import { DealFiltersComponent } from './deal-filters/deal-filters.component';
import type { DealCategories } from '../../shared/interfaces/deal-categories';
import { CategoriesService } from '../../shared/services/categories.service';
import { ProductsService } from '../../shared/services/products.service';
import type { ImportedDeal } from '../../shared/interfaces/deals';
import type { filterSend } from '../../shared/interfaces/products';
import { StoresService } from '../../shared/services/stores.service';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    CategoriesSectionComponent,
    HeroComponent,
    ProductsGridComponent,
    DealFiltersComponent,
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css',
})
export class MainPageComponent {
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);
  storesService = inject(StoresService);

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
  isLoading = true;
  hasError = false;
  StoreId = -1;

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
        console.error('Error fetching categories', error);
        this.hasError = true;
      }
    );
    this.storesService.getStores().subscribe({
      next: (data) => {
        this.storesList = data;
      },
      error: (error) => {
        console.log('Error while fetching stores', error);
        this.hasError = true;
      },
    });
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

          console.log(this.filter);
          this.totalPages = data.totalPages;
          this.pageSize = data.pageSize;
          this.totalElements = data.totalElements;
          console.log(this.totalPages);
          console.log(this.pagesNumber);
        },
        error: (error) => {
          console.error('Error fetching products', error);
          this.isLoading = false;
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

    console.log('STORE ID', this.StoreId);

    this.filter.category = String(filter[1]);
    this.filter.name = String(filter[2]);

    console.log('Received filter data:', filter);
    console.log(this.filter);
    this.getProductsFromSearch(this.filter);
  }
}
