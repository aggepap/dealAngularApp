import type { Store, StoreInsert } from '@/src/app/shared/interfaces/stores';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { Component, EventEmitter, inject, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-add-store-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-store-form.component.html',
  styleUrl: './add-store-form.component.css',
})
export class AddStoreFormComponent {
  @Output() cancelAdd = new EventEmitter<boolean>();

  OnCancelClick() {
    this.cancelAdd.emit(false);
  }
  onAddConfirm() {
    throw new Error('Method not implemented.');
  }
  storeService = inject(StoresService);

  addStoreForm = new FormGroup({
    addStoreName: new FormControl('Store Name', [
      Validators.required,
      Validators.minLength(3),
    ]),
    addStoreUrl: new FormControl(
      '',
      Validators.pattern(
        '((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)'
      )
    ),
    addStoreLogo: new FormControl(''),
  });

  onNewStoreAdd(value: any) {
    this.storeService
      .addStore(value.addStoreName, value.addStoreUrl, value.addStoreLogo)
      .subscribe({
        next: (data: Store) => {
          console.log(data);
        },
        error: (error) => {
          console.log('Error while adding category', error);
        },
        complete: () => {
          console.log('Store ${data.name} Succesfully added');
          window.location.reload();
        },
      });
  }
}
