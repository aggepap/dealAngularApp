import { Component, inject } from '@angular/core';
import { ProductsService } from '../../services/products.service';

@Component({
  selector: 'app-initialize-db',
  standalone: true,
  imports: [],
  templateUrl: './initialize-db.component.html',
  styleUrl: './initialize-db.component.css',
})
export class InitializeDbComponent {
  productsService = inject(ProductsService);

  ngOnInit() {
    this.productsService.initializeDB().subscribe({
      next: (data) => {},
      error: (error) => {},
      complete: () => {},
    });
  }
}
