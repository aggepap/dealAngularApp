import { Component, EventEmitter, inject, Input, Output } from '@angular/core';

import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import {
  CategoriesService,
  fontAwIcons,
} from '@/src/app/shared/services/categories.service';

@Component({
  selector: 'app-add-new-category',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-new-category.component.html',
  styleUrl: './add-new-category.component.css',
})
export class AddNewCategoryComponent {
  onIconChange($event: Event) {
    this.selectedIcon = ($event.target as HTMLInputElement).value;
  }
  fontAwIcons = fontAwIcons;
  selectedIcon = '';
  categoriesService = inject(CategoriesService);
  @Output() addIsEnabledChanged = new EventEmitter<boolean>();
  onCancelClick() {
    this.addIsEnabledChanged.emit(false);
  }

  insertNewForm = new FormGroup({
    newCategoryIcon: new FormControl('', Validators.required),
    newCategoryName: new FormControl('', Validators.required),
  });

  onNewCategoryAdd(value: any) {
    this.categoriesService
      .addCategory(value.newCategoryIcon.trim(), value.newCategoryName.trim())
      .subscribe(
        (data) => {
          console.log(data);
          console.log('Category Succesfully added');
          window.location.reload();
        },
        (error) => {
          console.log('Error while adding category', error);
        }
      );
  }
}
