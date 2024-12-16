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

@Component({
  selector: 'app-store-single-tab',
  standalone: true,
  imports: [
    UpdateStoreFormComponent,
    ProductsGridComponent,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './store-single-tab.component.html',
  styleUrl: './store-single-tab.component.css',
})
export class StoreSingleTabComponent {
  dealsList: ImportedDeal[] = [];
  categoriesList: DealCategories[] = [];
  storeService = inject(StoresService);
  productService = inject(ProductsService);
  editStoreEnabled = false;

  @Input() storeInfo!: Store;
  enableStoreEdit() {
    this.editStoreEnabled = !this.editStoreEnabled;
  }

  onDeleteStoreClick(id: number) {
    this.storeService.deleteStore(id);
  }
  onCancelClick(value: boolean) {
    this.editStoreEnabled = value;
  }

  filter = {
    name: '',
    category: '',
    store: this.storeInfo?.id,
    page: 0,
    size: 9,
  };

  ngOnChanges(changes: SimpleChanges) {
    if (changes['storeInfo']?.currentValue) {
      this.filter.store = this.storeInfo.id;
      console.log('Updated filter with store:', this.filter.store);
      this.getProductsFromSearch(this.filter);
    }
  }

  //Get products from search form
  //==============================================================================
  getProductsFromSearch(filters: filterSend) {
    if (this.filter.store === undefined) {
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
        },
        complete: () => {},
      });
  }
}
