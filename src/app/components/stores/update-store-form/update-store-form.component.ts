import { Store } from '@/src/app/shared/interfaces/stores';
import { fileTypeValidator } from '@/src/app/shared/services/customValidators';
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
  fileSelected = false;
  allowedFileTypes = [
    'image/gif',
    'image/png',
    'image/jpeg',
    'image/jpg',
    'image/webp',
  ];

  ngOnInit() {
    if (this.storeInfo) {
      this.updateStoreForm.patchValue({
        updatedStoreName: this.storeInfo.name,
        updatedStoreUrl: this.storeInfo.siteURL,
      });
    }
  }
  onUpdateConfirm(storeId: number, value: any) {
    console.log(value);
    const store = {
      name: value.updatedStoreName,
      siteURL: value.updatedStoreUrl,
    };
    const imageInput = document.getElementById(
      'updatedStoreLogo'
    ) as HTMLInputElement;

    const formData = new FormData();
    formData.append(
      'store',
      new Blob([JSON.stringify(store)], { type: 'application/json' })
    );
    formData.append('storeId', storeId.toString());
    // Check if an image was selected and send it
    if (imageInput?.files?.[0]) {
      const imageFile = imageInput.files[0];
      formData.append('image', imageFile);
      console.log(imageFile);
    }
    console.log(formData);

    this.storeService.updateStore(storeId, formData).subscribe({
      next: (data) => {
        console.log(data);
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
    updatedStoreName: new FormControl<string>('', Validators.required),
    updatedStoreUrl: new FormControl<string>('', Validators.required),
    updatedStoreLogo: new FormControl<string>(
      '',
      fileTypeValidator(this.allowedFileTypes)
    ),
  });

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
