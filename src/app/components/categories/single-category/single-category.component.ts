import { Component, inject } from '@angular/core';
import { ProductsGridComponent } from '../../products/products-grid/products-grid.component';
import type { ImportedDeal } from '@/src/app/shared/interfaces/deals';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import { filterSend } from '@/src/app/shared/interfaces/products';
import { LoadingSpinnerComponent } from '../../../shared/loading-spinner/loading-spinner.component';
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';

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
  dealsList: ImportedDeal[] = [];
  route = inject(ActivatedRoute);
  categoryService = inject(CategoriesService);
  location = inject(Location);

  productService = inject(ProductsService);
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

  filter = {
    name: '',
    category: '',
    page: 0,
    size: this.pageSize,
  };

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const categoryId = params.get('id');
      if (categoryId !== null) {
        this.categoryId = categoryId;
        console.log('ID from address bar:', this.categoryId);

        this.categoryService.getCategoryById(+this.categoryId).subscribe({
          next: (category) => {
            this.filter.category = String(category.id);
            this.categoryName = category.name;
            console.log('Category filter set:', this.filter.category);
            this.getProductsFromSearch(this.filter);
          },
          error: (error) => {
            console.error('Error fetching category:', error);
          },
          complete: () => {
            this.isLoading = false;
          },
        });
      }
    });
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
          console.error('Error fetching products:', error);
          this.isLoading = false;
          this.hasError = true;
        },
        complete: () => {
          console.log('Product fetching completed.');
        },
      });
  }

  back() {
    this.location.back();
  }
}
