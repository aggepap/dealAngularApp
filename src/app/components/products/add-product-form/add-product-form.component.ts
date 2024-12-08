import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import { Component, inject } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { fileTypeValidator } from '@/src/app/shared/services/fileTypeValidator';
import { ProductsService } from '@/src/app/shared/services/products.service';

@Component({
  selector: 'app-add-product-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-product-form.component.html',
  styleUrl: './add-product-form.component.css',
})
export class AddProductFormComponent {
  productService = inject(ProductsService);
  categoriesService = inject(CategoriesService);
  categoriesList: DealCategories[] = [];
  fileSelected = false;
  addProductSuccessMessage = false;
  allowedFileTypes = [
    'image/gif',
    'image/png',
    'image/jpeg',
    'image/jpg',
    'image/webp',
  ];

  ngOnInit() {
    this.addProductSuccessMessage = false;
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

  addProductForm = new FormGroup({
    addProductCategory: new FormControl(1, Validators.required),
    addProductName: new FormControl('', Validators.required),
    addProductDescription: new FormControl('', Validators.required),
    addProductImage: new FormControl('', [
      Validators.required,
      fileTypeValidator(this.allowedFileTypes),
    ]),
  });

  onNewProductAdd(value: any) {
    const product = {
      name: value.addProductName,
      description: value.addProductDescription,
    };
    const category = value.addProductCategory;

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
    if (category) {
      // formData.append('category', category);
      formData.append(
        'category',
        new Blob([category], { type: 'application/json' })
      );
    }
    // Check if an image was selected
    if (imageInput?.files?.[0]) {
      const imageFile = imageInput.files[0];
      formData.append('image', imageFile);
    }
    this.productService.addProduct(formData);
    this.addProductForm.reset();
    this.addProductSuccessMessage = true;
    this.fileSelected = false;
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
//
