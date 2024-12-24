import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import type { ImportedDeal } from '@/src/app/shared/interfaces/deals';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { environment } from '@/src/environments/environment.development';
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  Input,
} from '@angular/core';
import { LoadingSpinnerComponent } from '../../../shared/loading-spinner/loading-spinner.component';
import { ErrorMessageComponent } from '@/src/app/shared/components/error-message/error-message.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-products-grid',
  standalone: true,
  imports: [LoadingSpinnerComponent, ErrorMessageComponent, RouterLink],
  templateUrl: './products-grid.component.html',
  styleUrl: './products-grid.component.css',
})
export class ProductsGridComponent {
  //==============================================
  //  Service Injections
  //==============================================
  productService = inject(ProductsService);
  storeService = inject(StoresService);

  //==============================================
  //  Properties
  //==============================================
  apiUrl = environment.apiURL;
  hasError = false;
  isLoading = true;
  couponClicked = false;
  couponClickedId: number | null = null;

  // Hide the loading spinner after the view is initialized
  ngOnInit() {
    setTimeout(() => {
      this.isLoading = false;
    }, 100);
  }
  //==============================================
  //  Inputs
  //==============================================
  @Input({ required: true }) productsList: ImportedDeal[] = [];
  @Input() categoriesList?: DealCategories[] = [];
  @Input() basicGridSize: number | undefined;

  /**
   * Handles the click event on a coupon. Copies the coupon text to the clipboard and sets a flag to indicate that a coupon has been clicked.
   *
   * @param coupon The coupon code (string) to be copied to the clipboard.
   * @param dealId The ID of the deal associated with the clicked coupon.
   */
  onCouponClick(coupon: string, dealId: number) {
    navigator.clipboard.writeText(coupon);
    this.couponClicked = true;
    this.couponClickedId = dealId;
  }
}
