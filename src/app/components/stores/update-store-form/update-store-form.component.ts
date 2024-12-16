import { Store } from '@/src/app/shared/interfaces/stores';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-update-store-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './update-store-form.component.html',
  styleUrl: './update-store-form.component.css',
})
export class UpdateStoreFormComponent {
  storeService = inject(StoresService);
  @Input() storeInfo?: Store;
  @Output() cancelEdit = new EventEmitter<boolean>();
  OnCancelClick() {
    this.cancelEdit.emit(false);
  }
  onUpdateConfirm(id: number, value: any) {
    console.log(value);
    this.storeService
      .updateStore(
        id,
        value.updatedStoreName,
        value.updatedStoreUrl,
        value.updatedStoreLogo
      )
      .subscribe({
        next: (data) => {
          console.log(data);
          console.log(id, value.name, value.siteURL, value.logoURL);
          console.log('Store Succefully updates');
        },
        error: (error) => {
          console.log('Error  while updating store', error);
        },
        complete: () => {
          window.location.reload();
        },
      });
  }

  updateStoreForm = new FormGroup({
    updatedStoreName: new FormControl('', Validators.required),
    updatedStoreUrl: new FormControl('', Validators.required),
    updatedStoreLogo: new FormControl(''),
  });
}
