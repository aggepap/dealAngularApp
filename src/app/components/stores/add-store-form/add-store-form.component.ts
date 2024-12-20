import type { Store, StoreInsert } from '@/src/app/shared/interfaces/stores';
import { fileTypeValidator } from '@/src/app/shared/services/customValidators';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { Component, EventEmitter, inject, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  Form,
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
  fileSelected = false;
  allowedFileTypes = [
    'image/gif',
    'image/png',
    'image/jpeg',
    'image/jpg',
    'image/webp',
  ];

  OnCancelClick() {
    this.cancelAdd.emit(false);
  }
  onAddConfirm() {
    throw new Error('Method not implemented.');
  }
  storeService = inject(StoresService);

  addStoreForm = new FormGroup({
    addStoreName: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(3),
    ]),
    addStoreUrl: new FormControl<string>(
      '',
      Validators.pattern(
        '((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)'
      )
    ),
    addStoreLogo: new FormControl<string>('', [
      Validators.required,
      fileTypeValidator(this.allowedFileTypes),
    ]),
  });

  onNewStoreAdd(value: any) {
    const store = {
      name: value.addStoreName,
      siteURL: value.addStoreUrl,
    };
    const imageInput = document.getElementById(
      'addStoreLogo'
    ) as HTMLInputElement;

    const formData = new FormData();
    formData.append(
      'store',
      new Blob([JSON.stringify(store)], { type: 'application/json' })
    );

    // Check if an image was selected and send it
    if (imageInput?.files?.[0]) {
      const imageFile = imageInput.files[0];
      formData.append('logo', imageFile);
      console.log(imageFile);
    }
    this.storeService.addStore(formData).subscribe({
      next: (data: Store) => {
        console.log(data);
        console.log(`Store ${data.name} Succesfully added`);
      },
      error: (error) => {
        console.log('Error while adding Store', error);
      },
      complete: () => {
        window.location.reload();
      },
    });
  }

  checkFileType(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      const file = input.files[0];

      if (this.allowedFileTypes.includes(file.type)) {
        this.fileSelected = true;
      } else {
        this.fileSelected = false;
        input.value = '';
      }
    }
  }
}
