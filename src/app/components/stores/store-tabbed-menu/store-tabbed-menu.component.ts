import type { Store } from '@/src/app/shared/interfaces/stores';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { environment } from '@/src/environments/environment.development';
import { Component, inject } from '@angular/core';
import { StoreSingleTabComponent } from '../store-single-tab/store-single-tab.component';

@Component({
  selector: 'app-store-tabbed-menu',
  standalone: true,
  imports: [StoreSingleTabComponent],
  templateUrl: './store-tabbed-menu.component.html',
  styleUrl: './store-tabbed-menu.component.css',
})
export class StoreTabbedMenuComponent {
  apiURL = environment.apiURL;
  storeService = inject(StoresService);
  storeList: Store[] = [];
  pickedStore?: Store;

  ngOnInit() {
    this.storeService.getStores().subscribe(
      (data: Store[]) => {
        this.storeList = data;
        console.log(this.storeList);
      },
      (error) => console.log('Error while fetching Stores data')
    );
  }

  onStoreClick(id: string) {
    this.pickedStore = this.storeList.find((store) => store.id === id);
  }
}
