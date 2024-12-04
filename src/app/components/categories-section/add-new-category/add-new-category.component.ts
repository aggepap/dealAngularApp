import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { AllCategoriesViewComponent } from '../all-categories-view/all-categories-view.component';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CategoriesService } from '@/src/app/shared/services/categories.service';

@Component({
  selector: 'app-add-new-category',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-new-category.component.html',
  styleUrl: './add-new-category.component.css',
})
export class AddNewCategoryComponent {
  categoriesService = inject(CategoriesService);
  @Output() addIsEnabledChanged = new EventEmitter<boolean>();
  onCancelClick() {
    this.addIsEnabledChanged.emit(false);
  }

  insertNewForm = new FormGroup({
    newCategoryIcon: new FormControl('', [
      Validators.required,
      Validators.pattern('^fa-[a-z]+(?:-[a-z]+)*$'),
    ]),
    newCategoryName: new FormControl('', Validators.required),
  });

  onNewCategoryAdd(value: any) {
    this.categoriesService
      .addCategory(value.newCategoryIcon, value.newCategoryName)
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
