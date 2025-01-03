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
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-all-products-view',
  standalone: true,
  imports: [
    ProductsGridComponent,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    LoadingSpinnerComponent,
    ErrorMessageComponent,
    RouterLink,
  ],
  templateUrl: './all-products-view.component.html',
  styleUrl: './all-products-view.component.css',
})
export class AllProductsViewComponent {
  //==============================================
  //  Service Injections
  //==============================================
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);

  //==============================================
  //  Properties
  //==============================================
  categoriesList: DealCategories[] = [];
  dealsList: ImportedDeal[] = [];
  errorMessage = '';
  totalElements = 0;
  totalPages = 0;
  textFiltered = '';
  pagesNumber = 0;
  pageSize = 12;
  sortDirection = 'ASC';
  sortBy = 'name';
  //Boolean checks for loading and error
  isLoading = true;
  hasError = false;

  //==============================================
  //  ViewChild elements
  //==============================================
  @ViewChild('dropdownButton', { static: false }) dropdownButton!: ElementRef;
  @ViewChild('dropdownMainButton', { static: false })
  dropdownMainButton!: ElementRef;
  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;

  //==============================================
  //  Search Filter
  //==============================================
  filter = {
    name: '',
    category: '',
    page: this.pagesNumber,
    size: this.pageSize,
  };

  /**
   * Invoked once when the component is initialized.
   * Fetches initial data (categories) and retrieves products based on the current search filter.
   */
  ngOnInit() {
    this.getProductsFromSearch(this.filter);
    this.categoriesService.getCategories().subscribe({
      next: (data: DealCategories[]) => {
        this.categoriesList = data;
      },
      error: (error) => {
        this.hasError = true;
        this.errorMessage = 'Failed to get Categories';
      },
    });
  }
  /**
   * Handles the event when the user changes the current page number for pagination.
   * Updates the page number and retrieves filtered products based on the current filter.
   *
   * @param newPage The new page number to navigate to (starting from 0).
   */
  onPageChange(newPage: number): void {
    if (newPage < 0 || newPage > this.totalPages) {
      return; // Prevent invalid page numbers
    }
    this.pagesNumber = newPage;
    this.getProductsFromSearch(this.filter);
  }
  /**
   * FormGroup instance representing the search form for filtering products.
   * Contains controls for selecting a category and entering a search term.
   */
  searchForm = new FormGroup({
    categoriesSelect: new FormControl('', Validators.required),
    searchField: new FormControl('', [Validators.required]),
  });

  /**
   * Updates the displayed category name on the dropdown button based on the selected category.
   *
   * @param categoryName The selected category object containing the name property.
   */
  pickCategoryFromDropdown(categoryName: DealCategories) {
    const mainButton: HTMLElement = this.dropdownMainButton.nativeElement;
    mainButton.innerText = categoryName.name;
  }
  /**
   * Retrieves a filtered and paginated list of deals from the backend using the `productService`.
   * Updates component state with retrieved data (deals, total pages, and total elements).
   *
   * @param filters An object containing search filters (name, category, page, and size).
   */
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
          console.log(this.dealsList);

          this.pagesNumber = 0;
          this.totalPages = data.totalPages;
          this.pageSize = data.pageSize;
          this.totalElements = data.totalElements;
        },
        error: (error) => {
          this.isLoading = false;
          this.hasError = true;
          this.errorMessage = 'Failed to load products';
        },
        complete: () => {
          this.isLoading = false;
        },
      });
  }

  /**
   * Handles the event when the user changes the number of items displayed per page.
   * Resets the page number to 0 and retrieves filtered products based on the current filter and updated page size.
   */
  onPageSizeChange() {
    this.pagesNumber = 0;
    this.getProductsFromSearch(this.filter);
  }

  /**
   * Handles form value changes in the search form.
   * Updates the `filter` object with current search criteria and triggers a new product search.
   *
   * @param value An object containing the updated search form values.
   */
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
