import { Component, type ElementRef, inject, ViewChild } from '@angular/core';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import type { filterSend } from '@/src/app/shared/interfaces/products';
import { ProductsGridComponent } from '../products-grid/products-grid.component';
import type { ImportedDeal } from '@/src/app/shared/interfaces/deals';
import { CommonModule } from '@angular/common';
import {
  FormsModule,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { LoadingSpinnerComponent } from '@/src/app/shared/loading-spinner/loading-spinner.component';
@Component({
  selector: 'app-all-products-view',
  standalone: true,
  imports: [
    ProductsGridComponent,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    LoadingSpinnerComponent,
  ],
  templateUrl: './all-products-view.component.html',
  styleUrl: './all-products-view.component.css',
})
export class AllProductsViewComponent {
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);

  categoriesList: DealCategories[] = [];
  dealsList: ImportedDeal[] = [];
  totalElements = 0;
  totalPages = 0;
  textFiltered = '';
  pagesNumber = 0;
  pageSize = 12;
  sortDirection = 'ASC';
  sortBy = 'name';
  isLoading = true;

  @ViewChild('dropdownButton', { static: false }) dropdownButton!: ElementRef;
  @ViewChild('dropdownMainButton', { static: false })
  dropdownMainButton!: ElementRef;
  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;

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
      (error) => console.error('Error fetching categories', error)
    );
  }

  onPageChange(newPage: number): void {
    if (newPage < 0 || newPage > this.totalPages) {
      return; // Prevent invalid page numbers
    }

    this.pagesNumber = newPage;
    console.log(`Page changed to: ${this.pagesNumber}`);
    this.getProductsFromSearch(this.filter);
  }
  //Search Form
  //==============================================================================
  searchForm = new FormGroup({
    categoriesSelect: new FormControl('', Validators.required),
    searchField: new FormControl('', [Validators.required]),
  });

  //Change category name on select at dropdown list click
  //==============================================================================
  pickCategoryFromDropdown(categoryName: DealCategories) {
    const mainButton: HTMLElement = this.dropdownMainButton.nativeElement;
    mainButton.innerText = categoryName.name;
  }

  //Get products from search form
  //==============================================================================
  getProductsFromSearch(filters: filterSend) {
    this.productService
      .getProductsPaginatedFiltered(filters, this.pagesNumber, this.pageSize)
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

          console.log('Mapped DealList:', this.dealsList); // Debug final mapping
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

  onPageSizeChange() {
    this.getProductsFromSearch(this.filter);
  }

  generateSearchFilter(value: any) {
    this.filter = {
      name: value.searchField,
      category: value.categoriesSelect,
      page: this.pagesNumber,
      size: this.pageSize,
    };
    this.getProductsFromSearch(this.filter);
  }
}
