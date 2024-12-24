import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import { Component, inject } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { fileTypeValidator } from '@/src/app/shared/services/customValidators';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { StoresService } from '@/src/app/shared/services/stores.service';
import type { Store } from '@/src/app/shared/interfaces/stores';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from '../../../shared/loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-add-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, LoadingSpinnerComponent],
  templateUrl: './add-product-form.component.html',
  styleUrl: './add-product-form.component.css',
})
export class AddProductFormComponent {
  //==============================================
  //  Service Injections
  //==============================================
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);
  storeService = inject(StoresService);

  //==============================================
  //  Properties
  //==============================================
  categoriesList: DealCategories[] = [];
  storesList: Store[] = [];
  fileSelected = false;
  addProductSuccessMessage = false;
  allowedFileTypes = [
    'image/gif',
    'image/png',
    'image/jpeg',
    'image/jpg',
    'image/webp',
  ];
  //==============================================
  //  Boolen Checks for loading spinner
  //==============================================
  isLoading = true;

  /**
   * Invoked once when the component is initialized.
   * Fetches initial data (categories and stores) and sets loading state to false.
   */
  ngOnInit() {
    this.addProductSuccessMessage = false;
    this.getCategories();
    this.getStores();
    this.isLoading = false;
  }

  /**
   * FormGroup instance representing the form for adding a new product.
   * Contains controls for product category, name, description, image, store, URL, coupon, and price.
   */
  addProductForm = new FormGroup({
    addProductCategory: new FormControl(1, Validators.required),
    addProductName: new FormControl('', Validators.required),
    addProductDescription: new FormControl('', Validators.required),
    addProductImage: new FormControl('', [
      Validators.required,
      fileTypeValidator(this.allowedFileTypes),
    ]),
    addDealStore: new FormControl(1, Validators.required),
    AddDealURL: new FormControl('', [
      Validators.required,
      Validators.pattern(
        '((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)'
      ),
    ]),
    AddDealCoupon: new FormControl(''),
    AddDealPrice: new FormControl(0, Validators.required),
  });

  /**
   * Handles the form submission event for adding a new product.
   *
   * @param value An object containing the form data.
   */
  onNewProductAdd(value: any) {
    const product = {
      name: value.addProductName.trim(),
      description: value.addProductDescription.trim(),
      url: value.AddDealURL.trim(),
      coupon: value.AddDealCoupon.trim(),
      price: value.AddDealPrice,
    };
    const category = value.addProductCategory.trim();
    const store = value.addDealStore.trim();

    const imageInput = document.getElementById(
      'addProductImage'
    ) as HTMLInputElement;
    // Get image
    const image = document.getElementById(
      'addProductImage'
    ) as HTMLInputElement;

    const formData = new FormData();
    formData.append(
      'product',
      new Blob([JSON.stringify(product)], { type: 'application/json' })
    );
    //Send category part of formdata
    //==============================================================================
    if (category) {
      formData.append(
        'category',
        new Blob([category], { type: 'application/json' })
      );
    }
    //Send store part of formdata
    if (store) {
      formData.append('store', new Blob([store], { type: 'application/json' }));
    }
    // Check if an image was selected and send it
    //==============================================================================
    if (imageInput?.files?.[0]) {
      const imageFile = imageInput.files[0];
      formData.append('image', imageFile);
    }

    this.productService.addProduct(formData);
    this.addProductForm.reset();
    this.addProductSuccessMessage = true;
    this.fileSelected = false;
  }
  /**
   * Handles the file selection event for the product image upload.
   * Validates the selected file type against a list of allowed file types.
   *
   * @param event The event object triggered by file selection.
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

  /**
   * Retrieves the list of categories from the `categoriesService` for use in a dropdown.
   * Sorts the categories alphabetically by name.
   */
  getCategories() {
    this.categoriesService.getCategories().subscribe({
      next: (data: DealCategories[]) => {
        this.categoriesList = data.sort(
          (a: DealCategories, b: DealCategories) => {
            if (a.name < b.name) {
              return -1;
            }
            if (a.name > b.name) {
              return 1;
            }
            return 0;
          }
        );
        console.log(this.categoriesList);
      },
      error: (error) => console.error('Error fetching categories', error),
    });
  }

  /**
   * Retrieves the list of stores from the `storeService` for use in a dropdown.
   * Sorts the stores alphabetically by name.
   */
  getStores() {
    this.storeService.getStores().subscribe({
      next: (data: Store[]) => {
        this.storesList = data.sort((a: Store, b: Store) => {
          if (a.name < b.name) {
            return -1;
          }
          if (a.name > b.name) {
            return 1;
          }
          return 0;
        });
        console.log(this.storesList);
      },
      error: (error) => console.error('Error fetching stores', error),
    });
  }
}
//
