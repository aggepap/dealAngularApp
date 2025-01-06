import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import type {
  ProductData,
  ProductUpdate,
} from '@/src/app/shared/interfaces/products';
import type { Store } from '@/src/app/shared/interfaces/stores';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import { fileTypeValidator } from '@/src/app/shared/services/customValidators';
import { ProductsService } from '@/src/app/shared/services/products.service';
import { StoresService } from '@/src/app/shared/services/stores.service';
import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-update-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './update-product-form.component.html',
  styleUrl: './update-product-form.component.css',
})
export class UpdateProductFormComponent {
  //==============================================================================
  //Input/ Outputs / Service injections
  //==============================================================================
  @Input() product?: ProductData;
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);
  storeService = inject(StoresService);

  //==============================================================================
  // Properties
  //==============================================================================
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
  updateProductSuccessMessage = false;

  //==============================================================================
  //ngOnInit to get categories and stores on page load
  //==============================================================================
  ngOnInit() {
    this.addProductSuccessMessage = false;
    this.getCategories();
    this.getStores();

    //Pre-fill form with product data
    if (this.product) {
      const productImage = this.product.image
        ? this.product.image.savedName
        : null;
      this.updateProductForm.patchValue({
        updateProductCategory: this.product.category.id,
        updateProductName: this.product.name,
        updateProductDescription: this.product.description,
        updateDealStore: this.product.store.id,
        updateDealURL: this.product.url,
        updateProductImage: productImage,
        updateDealPrice: this.product.price,
        updateDealCoupon: this.product.coupon,
      });
    }
  }
  //==============================================================================
  //Add Product Form
  //==============================================================================
  updateProductForm = new FormGroup({
    updateProductCategory: new FormControl<number>(1, Validators.required),
    updateProductName: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(50),
    ]),
    updateProductDescription: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(1000),
    ]),
    updateProductImage: new FormControl<string>(
      '',
      fileTypeValidator(this.allowedFileTypes)
    ),
    updateDealStore: new FormControl<number>(1, Validators.required),
    updateDealURL: new FormControl<string>('', [
      Validators.required,
      Validators.pattern(
        '((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)'
      ),
    ]),
    updateDealCoupon: new FormControl<string>(''),
    updateDealPrice: new FormControl<number>(0, Validators.required),
  });

  /**
   * Handles the form submission for updating a product.
   * Creates a FormData object and sends it to the `productService` for updating the product.
   *
   * @param value The updated product data from the form.
   * @param productId The ID of the product to update.
   */
  onUpdateProduct(value: ProductUpdate, productId: number) {
    console.log('Updated value', value);

    const product = {
      name: value.updateProductName,
      description: value.updateProductDescription,
      url: value.updateDealURL,
      coupon: value.updateDealCoupon,
      price: value.updateDealPrice,
    };

    const categoryId = value.updateProductCategory;
    const storeId = value.updateDealStore;

    const imageInput = document.getElementById(
      'updateProductImage'
    ) as HTMLInputElement;

    const formData = new FormData();
    formData.append(
      'product',
      new Blob([JSON.stringify(product)], { type: 'application/json' })
    );

    if (categoryId) {
      formData.append('categoryId', categoryId.toString());
    }
    if (storeId) {
      formData.append('storeId', storeId.toString());
    }
    // Check if an image was selected and send it
    if (imageInput?.files?.[0]) {
      const imageFile = imageInput.files[0];
      formData.append('image', imageFile);
      console.log(imageFile);
    }

    this.productService.updateProduct(productId, formData);
    this.updateProductForm.reset();
    this.updateProductSuccessMessage = true;
    this.fileSelected = false;
  }
  /**
   * Checks the selected file type against the allowed file types.
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

  /**
   * Retrieves categories from the `categoriesService` and sorts them alphabetically.
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
      },
      error: (error) => console.error('Error fetching categories', error),
    });
  }

  /**
   * Retrieves stores from the `storeService` and sorts them alphabetically.
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
      },
      error: (error) => console.error('Error fetching stores', error),
    });
  }
  getFormValue(): ProductUpdate {
    return this.updateProductForm.value as ProductUpdate;
  }
}
