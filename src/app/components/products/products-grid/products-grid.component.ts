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

@Component({
  selector: 'app-products-grid',
  standalone: true,
  imports: [],
  templateUrl: './products-grid.component.html',
  styleUrl: './products-grid.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductsGridComponent {
  productService = inject(ProductsService);
  storeService = inject(StoresService);
  apiUrl = environment.apiURL;
  @Input({ required: true }) productsList: ImportedDeal[] = [];
  @Input({ required: true }) categoriesList: DealCategories[] = [];
  @Input() basicGridSize: number | undefined;
}
