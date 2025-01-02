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
  //==============================================================================
  // Inputs / Outputs / Service injections
  //==============================================================================
  storeService = inject(StoresService);
  productService = inject(ProductsService);
  userService = inject(UsersService);
  @Input() storeInfo!: Store;

  //==============================================================================
  // Properties
  //==============================================================================
  dealsList: ImportedDeal[] = [];
  categoriesList: DealCategories[] = [];
  user = this.userService.user;
  isLoading = true;
  hasError = false;
  editStoreEnabled = false;
  totalElements = 0;
  pagesNumber = 0;
  totalPages = 0;
  pageSize = 9;

  filter = {
    name: '',
    category: '',
    store: undefined as number | undefined,
    page: 0,
    size: this.pageSize,
  };

  /**
   * Enables or disables the store edit form.
   */
  enableStoreEdit() {
    this.editStoreEnabled = !this.editStoreEnabled;
  }

  /**
   * Handles the click event for deleting a store.
   * @param id The ID of the store to delete.
   */
  onDeleteStoreClick(id: number) {
    this.storeService.deleteStore(id);
  }

  /**
   * Handles the cancel event from the `UpdateStoreFormComponent`.
   * @param value A boolean indicating whether editing was cancelled.
   */
  onCancelClick(value: boolean) {
    this.editStoreEnabled = value;
  }
  /**
   * Lifecycle hook called when input properties change.
   * Updates the product filter and fetches products when the `storeInfo` input changes.
   * @param changes Object containing the changed input properties.
   */
  ngOnChanges(changes: SimpleChanges) {
    if (changes['storeInfo']?.currentValue) {
      this.filter.store = this.storeInfo.id;
      this.pagesNumber = 0; // Reset page number on store change
      this.filter.page = this.pagesNumber; // Update filter
      this.getProductsFromSearch(this.filter); // Fetch products for the new store
      this.editStoreEnabled = false;
    }
  }

  /**
   * Handles pagination changes.
   * Updates the current page number and fetches products for the new page.
   * @param newPage The new page number.
   */
  onPageChange(newPage: number): void {
    if (newPage < 0 || newPage >= this.totalPages) {
      return;
    }
    this.pagesNumber = newPage;
    this.filter.page = this.pagesNumber;
    this.getProductsFromSearch(this.filter);
  }

  /**
   * Handles page size changes.
   * Resets the page number and fetches products with the new page size.
   */
  onPageSizeChange(): void {
    this.filter.size = this.pageSize;
    this.pagesNumber = 0;
    this.filter.page = this.pagesNumber;
    this.getProductsFromSearch(this.filter);
  }

  /**
   * Fetches products from the server based on the provided filter.
   * @param filters The filter criteria for fetching products.
   */
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
          this.isLoading = false;
        },
      });
  }
}
