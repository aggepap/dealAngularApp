import { Component, inject } from '@angular/core';
import { ProductsService } from '../../services/products.service';
import { Router, RouterLink } from '@angular/router';
import { ErrorService } from '../../services/error.service';

@Component({
  selector: 'app-initialize-db',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './initialize-db.component.html',
  styleUrl: './initialize-db.component.css',
})
export class InitializeDbComponent {
  //==============================================================================
  //Service injections
  //==============================================================================
  router = inject(Router);
  productsService = inject(ProductsService);
  errorService = inject(ErrorService);
  initializeSuccess = false;

  /**
   * Initializes the database by calling the `initializeDB` method of the `productsService`.
   * Handles success, error, and completion scenarios with appropriate user feedback and navigation.
   */
  onInitializeDB() {
    this.productsService.initializeDB().subscribe({
      next: (data) => {
        this.errorService.errorMessage.set('Database initialized successfully');
        this.errorService.errorColor.set('green');
        this.initializeSuccess = true;
      },
      error: (error) => {
        console.log(error);

        this.errorService.errorMessage.set(
          `${error.message} | You have already initialized the database or your database tables are not emtpy`
        );
        this.errorService.errorColor.set('red');
      },
      complete: () => {
        setTimeout(() => {
          this.router.navigate(['/']);
        }, 2000);
      },
    });
  }
}
