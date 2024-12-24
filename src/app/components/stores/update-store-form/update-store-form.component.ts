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
  //==============================================================================
  // Inputs / Outputs / Service injections
  //==============================================================================
  storeService = inject(StoresService);
  @Input() storeInfo?: Store;
  @Output() cancelEdit = new EventEmitter<boolean>();

  //==============================================================================
  // Properties
  //==============================================================================
  fileSelected = false;
  allowedFileTypes = [
    'image/gif',
    'image/png',
    'image/jpeg',
    'image/jpg',
    'image/webp',
  ];

  /**
   * If `storeInfo` is provided, pre-populates the form with the store name and URL.
   */
  ngOnInit() {
    if (this.storeInfo) {
      this.updateStoreForm.patchValue({
        updatedStoreName: this.storeInfo.name,
        updatedStoreUrl: this.storeInfo.siteURL,
      });
    }
  }
  /**
   * Handles the click event on the cancel button.
   * Emits a `false` value through the `cancelEdit` output event to indicate cancellation.
   */
  OnCancelClick() {
    this.cancelEdit.emit(false);
  }

  /**
   * Handles the confirmation of store update.
   * @param storeId The numeric ID of the store to be updated.
   * @param value The form data containing the updated store name and URL.
   */
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

  /**
   * The reactive form group for the store update form.
   */
  updateStoreForm = new FormGroup({
    updatedStoreName: new FormControl<string>('', Validators.required),
    updatedStoreUrl: new FormControl<string>('', Validators.required),
    updatedStoreLogo: new FormControl<string>(
      '',
      fileTypeValidator(this.allowedFileTypes)
    ),
  });

  /**
   * Handles the change event of the file input.
   * @param event The change event object.
   * Sets the `fileSelected` property to `true` if a file is selected and its type is allowed.
   * If the file type is not allowed, clears the input value and sets `fileSelected` to `false`.
   * @param event The change event object.
   * @returns void
   */
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
