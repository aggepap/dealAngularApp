import { Component, inject } from '@angular/core';
import { ProductsGridComponent } from '../../products/products-grid/products-grid.component';
import type { ImportedDeal } from '@/src/app/shared/interfaces/deals';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import type { filterSend } from '@/src/app/shared/interfaces/products';
import { LoadingSpinnerComponent } from '../../../shared/loading-spinner/loading-spinner.component';
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';
import { ErrorService } from '@/src/app/shared/services/error.service';

@Component({
  selector: 'app-single-category',
  standalone: true,
  imports: [
    ProductsGridComponent,
    CommonModule,
    FormsModule,
    LoadingSpinnerComponent,
    ErrorMessageComponent,
  ],
  templateUrl: './single-category.component.html',
  styleUrl: './single-category.component.css',
})
export class SingleCategoryComponent {
  //==============================================
  //  Services injection
  //==============================================
  route = inject(ActivatedRoute);
  categoryService = inject(CategoriesService);
  location = inject(Location);
  productService = inject(ProductsService);
  errorService = inject(ErrorService);
  //==============================================
  //  Properties
  //==============================================
  dealsList: ImportedDeal[] = [];
  editStoreEnabled = false;
  totalElements = 0;
  pagesNumber = 0;
  totalPages = 0;
  pageSize = 12;
  categoryName = '';
  categoryId = '';
  isLoading = true;
  hasError = false;
  category: any = null;

  //==============================================
  //  Search Filter
  //==============================================
  filter = {
    name: '',
    category: '',
    page: 0,
    size: this.pageSize,
  };
  //==============================================
  //  Gets category id from params and fetches category data
  //==============================================
  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const categoryId = params.get('id');
      if (categoryId !== null) {
        this.categoryId = categoryId;

        this.categoryService.getCategoryById(+this.categoryId).subscribe({
          next: (category) => {
            this.filter.category = String(category.id);
            this.categoryName = category.name;
            this.getProductsFromSearch(this.filter);
          },
          error: (error) => {
            this.isLoading = false;
            this.hasError = true;
            this.errorService.errorMessage.set('Error fetching category');
            this.errorService.errorColor.set('red');
          },
          complete: () => {
            this.isLoading = false;
          },
        });
      }
    });
  }

  /**
   * Handles the event when the user changes the current page number for pagination.
   *
   * @param newPage The new page number to navigate to (starting from 0).
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
   * Handles the event when the user changes the number of items displayed per page.
   */
  onPageSizeChange(): void {
    this.filter.size = this.pageSize;
    this.pagesNumber = 0;
    this.filter.page = this.pagesNumber;

    this.getProductsFromSearch(this.filter);
  }

  /**
   * Retrieves a filtered and paginated list of products from the backend using the `productService`.
   *
   * @param filters An object containing search filters to be applied.
   */
  getProductsFromSearch(filters: filterSend) {
    this.productService
      .getProductsPaginatedFiltered(filters, this.filter.page, this.filter.size)
      .subscribe({
        next: (data: any) => {
          this.dealsList = data.data.map((deal: any) => ({
            id: deal.id,
            name: deal.name,
            description: deal.description,
            categoryName: deal.category?.name || '',
            categoryId: deal.category?.id || 0,
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
          this.isLoading = false;
          this.hasError = true;
          this.errorService.errorMessage.set('Error fetching products');
          this.errorService.errorColor.set('red');
        },
        complete: () => {
          this.isLoading = false;
        },
      });
  }

  /**
   * Navigates back to the previous page using the Angular `location` service.
   */
  back() {
    this.location.back();
  }
}
