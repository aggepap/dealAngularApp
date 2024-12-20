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
  type SimpleChanges,
} from '@angular/core';
import { LoadingSpinnerComponent } from '../../../shared/loading-spinner/loading-spinner.component';
import { ErrorMessageComponent } from '@/src/app/shared/components/error-message/error-message.component';

@Component({
  selector: 'app-products-grid',
  standalone: true,
  imports: [LoadingSpinnerComponent, ErrorMessageComponent],
  templateUrl: './products-grid.component.html',
  styleUrl: './products-grid.component.css',
})
export class ProductsGridComponent {
  productService = inject(ProductsService);
  storeService = inject(StoresService);
  apiUrl = environment.apiURL;
  hasError = false;
  isLoading = true;

  ngAfterViewInit() {
    this.isLoading = false;
  }
  @Input({ required: true }) productsList: ImportedDeal[] = [];
  @Input() categoriesList?: DealCategories[] = [];
  @Input() basicGridSize: number | undefined;
}
