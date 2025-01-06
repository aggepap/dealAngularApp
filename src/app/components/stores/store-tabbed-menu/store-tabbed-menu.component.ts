import type { Store } from '@/src/app/shared/interfaces/stores';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { environment } from '@/src/environments/environment.development';
import { Component, inject, Input } from '@angular/core';
import { StoreSingleTabComponent } from '../store-single-tab/store-single-tab.component';
import { ErrorMessageComponent } from '../../../shared/components/error-message/error-message.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-store-tabbed-menu',
  standalone: true,
  imports: [StoreSingleTabComponent, ErrorMessageComponent],
  templateUrl: './store-tabbed-menu.component.html',
  styleUrl: './store-tabbed-menu.component.css',
})
export class StoreTabbedMenuComponent {
  //==============================================================================
  // Inputs / Outputs / Service injections
  //==============================================================================
  storeService = inject(StoresService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  //==============================================================================
  // Properties
  //==============================================================================
  apiURL = environment.apiURL;
  storeList: Store[] = [];
  @Input() pickedStore?: Store;
  isError = false;
  errorMessage = '';

  /**
   * Fetches the list of stores from the stores API and subscribes to the observable returned by the `getStores` method of the `storeService`.
   * Sets the `storeList` property with the retrieved data or sets the `isError` and `errorMessage` properties in case of errors.
   */
  ngOnInit() {
    const storeId = this.route.snapshot.paramMap.get('storeId');

    this.storeService.getStores().subscribe({
      next: (data: Store[]) => {
        this.storeList = data;
        if (storeId) {
          this.pickedStore = this.storeList.find(
            (store) => store.id === +storeId
          );
        }
        if (storeId && !this.pickedStore) {
          this.isError = true;
          this.errorMessage = `Store with id ${storeId} not found`;
        }
      },
      error: (error) => {
        console.log('Error while fetching Stores data', error);
        this.isError = true;
        this.errorMessage = 'Error while fetching Stores data';
        console.log('Error while fetching Stores data');
      },
    });
  }
  /**
   * Handles user clicks on a store item in the component's template.
   * Takes an `id` parameter representing the ID of the clicked store.
   * Finds the store object from the `storeList` that matches the provided `id` and assigns it to the `pickedStore` property.
   * This allows displaying detailed information about the selected store.
   * @param id The numeric ID of the clicked store.
   */
  onStoreClick(id: number) {
    this.pickedStore = this.storeList.find((store) => store.id === id);

    this.router.navigate(['/stores', id], { replaceUrl: true });
  }
}
