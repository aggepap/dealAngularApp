import { ProductsService } from '@/src/app/shared/services/products.service';
import { environment } from '@/src/environments/environment.development';
import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LoadingSpinnerComponent } from '@/src/app/shared/loading-spinner/loading-spinner.component';
import { ProductNotFoundComponent } from './product-not-found/product-not-found.component';
import { UpdateProductFormComponent } from '../update-product-form/update-product-form.component';
import type { ProductData } from '@/src/app/shared/interfaces/products';
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';
import { UsersService } from '@/src/app/shared/services/users.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-single-product',
  standalone: true,
  imports: [
    LoadingSpinnerComponent,
    ProductNotFoundComponent,
    UpdateProductFormComponent,
    ErrorMessageComponent,
    RouterLink,
  ],
  templateUrl: './single-product.component.html',
  styleUrl: './single-product.component.css',
})
export class SingleProductComponent {
  //==============================================================================
  //Service injections
  //==============================================================================
  productService = inject(ProductsService);
  userService = inject(UsersService);
  route = inject(ActivatedRoute);
  //==============================================================================
  //  Properties
  //==============================================================================
  editProductIsEnabled = false;
  user = this.userService.user;
  productId!: number;
  apiUrl = environment.apiURL;
  isLoading = true;
  hasError = false;
  product?: ProductData;
  couponClicked = false;

  //==============================================================================
  //  ngOnInit
  //==============================================================================
  ngOnInit() {
    //gets id from browsers address bar and stores it to productid
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productId = +id;
    } else {
      console.error('No product ID found in route parameters!');
    }

    //gets the product by the id that was stored in productId
    this.getProductById(this.productId);
  }

  /**
   * Calls the `deleteProduct` service method when the delete button is clicked.
   */
  onDeleteProductClick() {
    this.productService.deleteProduct(this.productId);
  }

  /**
   * Toggles the visibility of the product edit form.
   */
  enableProductEdit() {
    this.editProductIsEnabled = !this.editProductIsEnabled;
  }

  /**
   * Handles the click event on a coupon. Copies the coupon text to the clipboard and sets the `couponClicked` flag.
   * @param coupon The coupon code to copy.
   */
  onCouponClick(coupon: string) {
    navigator.clipboard.writeText(coupon);
    this.couponClicked = true;
  }

  /**
   * Uses `productService` to find product with provided ID.
   * @param id The ID of the product to retrieve.
   */
  getProductById(id: number) {
    this.productService.getProductById(id).subscribe({
      next: (data: ProductData) => {
        this.product = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error fetching product', error);

        this.isLoading = false;
        this.hasError = true;
      },
    });
  }
}
