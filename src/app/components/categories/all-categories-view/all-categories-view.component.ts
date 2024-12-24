import { Component, inject } from '@angular/core';
import { AddNewCategoryComponent } from '../add-new-category/add-new-category.component';
import {
  CategoriesService,
  fontAwIcons,
} from '@/src/app/shared/services/categories.service';
import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { UsersService } from '@/src/app/shared/services/users.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-all-categories-view',
  standalone: true,
  imports: [ReactiveFormsModule, AddNewCategoryComponent, RouterLink],
  templateUrl: './all-categories-view.component.html',
  styleUrl: './all-categories-view.component.css',
})
export class AllCategoriesViewComponent {
  //==============================================
  //  Services injection
  //==============================================
  categoriesService = inject(CategoriesService);
  userService = inject(UsersService);

  //==============================================
  //  Properties
  //==============================================
  categoriesList: DealCategories[] = [];
  addIsEnabled = false;
  fontAwIcons = fontAwIcons;
  selectedIcon = '';
  user = this.userService.user;

  //==============================================
  //  Get Categories from DB on init
  //==============================================
  ngOnInit() {
    this.categoriesService.getCategories().subscribe(
      (data: DealCategories[]) => {
        this.categoriesList = data;
      },
      (error) => console.error('Error fetching categories', error)
    );
  }

  /**
   * Handles the click event on the "Update" button for a specific category.
   * Toggles the visibility of the update form associated with that category ID.
   *
   * @param id The ID of the category to update.
   */
  onUpdateClick(id: number) {
    const el = document.getElementById(`update-form-${id}`);
    el?.classList.toggle('hidden');
  }

  /**
   * Handles the click event on the "Delete" button for a specific category.
   * Calls the `categoriesService` to delete the category using its ID.
   *
   * @param id The ID of the category to delete.
   */
  onDeleteClick(id: number) {
    this.categoriesService.deleteCategory(id);
  }

  /**
   * Handles the event when the user changes the new category icon selection.
   *
   * @param event The event object triggered by the icon selection change.
   */
  onIconChange($event: Event) {
    this.selectedIcon = ($event.target as HTMLInputElement).value;
  }

  /**
   * Handles the confirmation event when the user submits the updated category information.
   *
   * @param id The ID of the category to update.
   * @param value An object containing the updated category data (extracted from the form).
   */
  onUpdateConfirm(id: number, value: any) {
    console.log(value);
    const icon = value['cat-update-icon'];
    const name = value['cat-update-name'].trim();
    console.log(icon, name);
    this.categoriesService
      .updateCategory(id, value['cat-update-icon'], value['cat-update-name'])
      .subscribe(
        (response) => {
          alert(`Category ${value['cat-update-name']} was Updated`);
          window.location.reload();
        },
        (error) => {
          alert('Error while updating category');
          console.log('Error while updating category', error);
        }
      );
  }

  /**
   * Handles the click event on the "Cancel" button for a specific category update form.
   * Hides the update form associated with that category ID.
   *
   * @param id The ID of the category whose update form needs to be hidden.
   */
  OnCancelClick(id: number) {
    const el = document.getElementById(`update-form-${id}`);
    el?.classList.add('hidden');
  }

  /**
   * FormGroup instance containing form controls for updating category icon and name.
   */
  updateForm = new FormGroup({
    'cat-update-icon': new FormControl('', [
      Validators.required,
      Validators.pattern('^fa-[a-z]+(?:-[a-z]+)*$'),
    ]),
    'cat-update-name': new FormControl('', Validators.required),
  });

  /**
   * Toggles the value of `addIsEnabled` to control the visibility of the "Add New Category" form.
   */
  onAddNewClick() {
    this.addIsEnabled = !this.addIsEnabled;
  }

  /**
   * Handles the change event emitted by the `addIsEnabledChanged` component output.
   * Updates the local `addIsEnabled` flag based on the emitted value.
   *
   * @param value A boolean value indicating whether "Add New Category" functionality should be enabled.
   */
  handleAddIsEnabledChange(value: boolean) {
    this.addIsEnabled = value;
  }
}
