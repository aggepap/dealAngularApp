import { Component, inject, Input, type SimpleChanges } from '@angular/core';
import { UpdateStoreFormComponent } from '../update-store-form/update-store-form.component';
import type { Store } from '@/src/app/shared/interfaces/stores';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { ProductsGridComponent } from '../../products/products-grid/products-grid.component';
import type { ImportedDeal } from '@/src/app/shared/interfaces/deals';
import type { filterSend } from '@/src/app/shared/interfaces/products';
import { ProductsService } from '@/src/app/shared/services/products.service';
import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';
import { LoadingSpinnerComponent } from '../../../shared/loading-spinner/loading-spinner.component';
import { UsersService } from '@/src/app/shared/services/users.service';

@Component({
  selector: 'app-store-single-tab',
  standalone: true,
  imports: [
    UpdateStoreFormComponent,
    ProductsGridComponent,
    CommonModule,
    FormsModule,
    LoadingSpinnerComponent,
    ErrorMessageComponent,
  ],
  templateUrl: './store-single-tab.component.html',
  styleUrl: './store-single-tab.component.css',
})
export class StoreSingleTabComponent {
  dealsList: ImportedDeal[] = [];
  categoriesList: DealCategories[] = [];
  storeService = inject(StoresService);
  productService = inject(ProductsService);
  userService = inject(UsersService);
  user = this.userService.user;
  isLoading = true;
  hasError = false;
  editStoreEnabled = false;
  totalElements = 0;
  pagesNumber = 0;
  totalPages = 0;
  pageSize = 9;

  @Input() storeInfo!: Store;

  filter = {
    name: '',
    category: '',
    store: undefined as number | undefined,
    page: 0,
    size: this.pageSize,
  };

  enableStoreEdit() {
    this.editStoreEnabled = !this.editStoreEnabled;
  }

  onDeleteStoreClick(id: number) {
    this.storeService.deleteStore(id);
  }

  onCancelClick(value: boolean) {
    this.editStoreEnabled = value;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['storeInfo']?.currentValue) {
      this.filter.store = this.storeInfo.id;
      this.pagesNumber = 0; // Reset page number on store change
      this.filter.page = this.pagesNumber; // Update filter
      console.log('Updated filter with store:', this.filter.store);
      this.getProductsFromSearch(this.filter); // Fetch products for the new store
    }
  }

  onPageChange(newPage: number): void {
    if (newPage < 0 || newPage >= this.totalPages) {
      return;
    }

    this.pagesNumber = newPage;
    this.filter.page = this.pagesNumber;
    console.log(`Page changed to: ${this.pagesNumber}`);
    this.getProductsFromSearch(this.filter);
  }

  // Handle page size change
  onPageSizeChange(): void {
    this.filter.size = this.pageSize;
    this.pagesNumber = 0;
    this.filter.page = this.pagesNumber;
    console.log(`Page size changed to: ${this.pageSize}`);
    this.getProductsFromSearch(this.filter);
  }

  getProductsFromSearch(filters: filterSend) {
    if (this.filter.store === undefined) {
      console.warn('Store is undefined in filter');
      return;
    }

    this.productService
      .getStoreProductsPaginatedFiltered(
        filters,
        this.filter.page,
        this.filter.size,
        this.filter.store
      )
      .subscribe({
        next: (data: any) => {
          console.log('API Response Data:', data);
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
          this.totalElements = data.totalElements;
        },
        error: (error) => {
          console.error('Error fetching products:', error);
          this.isLoading = false;
          this.hasError = true;
        },
        complete: () => {
          console.log('Product fetching completed.');
          this.isLoading = false;
        },
      });
  }
}
