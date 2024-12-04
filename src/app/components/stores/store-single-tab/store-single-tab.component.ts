import { Component, inject, Input } from '@angular/core';
import { UpdateStoreFormComponent } from '../update-store-form/update-store-form.component';
import type { Store } from '@/src/app/shared/interfaces/stores';
import { StoresService } from '@/src/app/shared/services/stores.service';

@Component({
  selector: 'app-store-single-tab',
  standalone: true,
  imports: [UpdateStoreFormComponent],
  templateUrl: './store-single-tab.component.html',
  styleUrl: './store-single-tab.component.css',
})
export class StoreSingleTabComponent {
  storeService = inject(StoresService);
  editStoreEnabled = false;

  @Input() storeInfo: Store | undefined;
  enableStoreEdit() {
    this.editStoreEnabled = !this.editStoreEnabled;
  }

  onDeleteStoreClick(id: string) {
    this.storeService.deleteStore(id);
  }
  onCancelClick(value: boolean) {
    this.editStoreEnabled = value;
  }
}
