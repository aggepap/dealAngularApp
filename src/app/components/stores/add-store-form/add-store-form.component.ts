import type { Store } from '@/src/app/shared/interfaces/stores';
import { fileTypeValidator } from '@/src/app/shared/services/customValidators';
import { ErrorService } from '@/src/app/shared/services/error.service';
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
  //==============================================================================
  //Input/ Outputs / Service injections
  //==============================================================================
  @Output() cancelAdd = new EventEmitter<boolean>();
  storeService = inject(StoresService);
  errorService = inject(ErrorService);

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
   * Handles the user clicking the cancel button.
   * Emits the `cancelAdd` event with `false` to indicate cancellation.
   */
  OnCancelClick() {
    this.cancelAdd.emit(false);
  }

  /**
   * Reactive form group for adding a new store.
   */
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

  /**
   * Handles form submission for adding a new store.
   * Creates a FormData object with store data and logo (if selected) and sends it to the `storeService` for adding the store.
   *
   * @param value The form data containing store name and URL.
   */
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
        this.errorService.errorMessage.set('Store added successfully');
        this.errorService.errorColor.set('green');
      },
      error: (error) => {
        this.errorService.errorMessage.set('Error while adding Store');
        this.errorService.errorColor.set('red');
      },
      complete: () => {
        window.location.reload();
      },
    });
  }

  /**
   * Checks the selected file type against the allowed file types.
   * Clears the file input if the selected file type is not allowed.
   *
   * @param event The file input change event.
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
