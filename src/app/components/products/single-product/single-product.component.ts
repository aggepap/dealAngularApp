import { ProductsService } from '@/src/app/shared/services/products.service';
import { environment } from '@/src/environments/environment.development';
import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LoadingSpinnerComponent } from '@/src/app/shared/loading-spinner/loading-spinner.component';
import { ProductNotFoundComponent } from './product-not-found/product-not-found.component';
import { UpdateProductFormComponent } from '../update-product-form/update-product-form.component';
import type { ProductData } from '@/src/app/shared/interfaces/products';
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';

@Component({
  selector: 'app-single-product',
  standalone: true,
  imports: [
    LoadingSpinnerComponent,
    ProductNotFoundComponent,
    UpdateProductFormComponent,
    ErrorMessageComponent,
  ],
  templateUrl: './single-product.component.html',
  styleUrl: './single-product.component.css',
})
export class SingleProductComponent {
  editProductIsEnabled: boolean = false;

  onDeleteProductClick() {
    this.productService.deleteProduct(this.productId);
  }

  enableProductEdit() {
    this.editProductIsEnabled = !this.editProductIsEnabled;
  }
  productService = inject(ProductsService);
  productId!: number;
  apiUrl = environment.apiURL;
  isLoading = true;
  hasError = false;
  product?: ProductData;

  constructor(private route: ActivatedRoute) {}
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productId = +id;
    } else {
      console.error('No product ID found in route parameters!');
    }
    console.log(this.productId);

    this.getProductById(this.productId);
  }

  getProductById(id: number) {
    this.productService.getProductById(id).subscribe({
      next: (data: ProductData) => {
        this.product = data;
        this.isLoading = false;
        console.log(this.product);
      },
      error: (error) => {
        console.error('Error fetching product', error);

        this.isLoading = false;
        this.hasError = true;
      },
    });
  }
}
