import { ProductsService } from '@/src/app/shared/services/products.service';
import { environment } from '@/src/environments/environment.development';
import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LoadingSpinnerComponent } from '@/src/app/shared/loading-spinner/loading-spinner.component';
import { ProductNotFoundComponent } from './product-not-found/product-not-found.component';

@Component({
  selector: 'app-single-product',
  standalone: true,
  imports: [LoadingSpinnerComponent, ProductNotFoundComponent],
  templateUrl: './single-product.component.html',
  styleUrl: './single-product.component.css',
})
export class SingleProductComponent {
  productService = inject(ProductsService);
  productId!: number;
  apiUrl = environment.apiURL;
  isLoading = true;
  product: any;

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
      next: (data: any) => {
        this.product = data;
        this.isLoading = false;
        console.log(this.product);
      },
      error: (error) => {
        console.error('Error fetching product', error);
        this.isLoading = false;
      },
    });
  }
}
