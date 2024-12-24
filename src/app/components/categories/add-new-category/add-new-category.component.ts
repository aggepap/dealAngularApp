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
  //======================================================
  //  Inject Services and Outputs
  //======================================================
  categoriesService = inject(CategoriesService);
  @Output() addIsEnabledChanged = new EventEmitter<boolean>();

  //======================================================
  //  Properties
  //======================================================
  fontAwIcons = fontAwIcons;
  selectedIcon = '';

  /**
   * FormGroup instance containing form controls for new category name and icon.
   */
  insertNewForm = new FormGroup({
    newCategoryIcon: new FormControl('', Validators.required),
    newCategoryName: new FormControl('', Validators.required),
  });

  /**
   * Handles the event when the user changes the new category icon selection.
   *
   * @param event The event object triggered by the icon selection change.
   */
  onIconChange($event: Event) {
    this.selectedIcon = ($event.target as HTMLInputElement).value;
  }

  /**
   * Handles the event when the user clicks the cancel button for adding a new category.
   *
   * @param addIsEnabledChanged Emits a boolean value to indicate whether the "Add New Category" functionality is enabled or not.
   */
  onCancelClick() {
    this.addIsEnabledChanged.emit(false);
  }

  /**
   * Handles the event when the user submits the form to add a new category.
   *
   * @param value An object containing the form control values (`newCategoryIcon` and `newCategoryName`).
   * @returns A Promise that resolves when the new category is added successfully.
   */
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