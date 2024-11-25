import { Component, inject } from '@angular/core';
import { AddNewCategoryComponent } from '../add-new-category/add-new-category.component';
import { CategoriesService } from '@/src/app/shared/services/categories.service';
import type { DealCategories } from '@/src/app/shared/interfaces/deal-categories';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-all-categories-view',
  standalone: true,
  imports: [ReactiveFormsModule, AddNewCategoryComponent],
  templateUrl: './all-categories-view.component.html',
  styleUrl: './all-categories-view.component.css',
})
export class AllCategoriesViewComponent {
  categoriesService = inject(CategoriesService);
  categoriesList: DealCategories[] = [];
  addIsEnabled = false;

  ngOnInit() {
    this.categoriesService.getCategories().subscribe(
      (data: DealCategories) => {
        console.log(data);
        data.forEach((item) => {
          this.categoriesList.push(item);
        });
      },
      (error) => console.error('Error fetching categories', error)
    );
    console.log(this.categoriesList);
  }

  onUpdateClick(id: string) {
    const el = document.getElementById(`update-form-${id}`);
    el?.classList.toggle('hidden');
  }

  onDeleteClick(id: string) {
    this.categoriesService.deleteCategory(id);
  }
  onUpdateConfirm(id: string, value: any) {
    console.log(value);
    const icon = value['cat-update-icon'];
    const name = value['cat-update-name'];
    console.log(icon, name);
    this.categoriesService
      .updateCategory(id, value['cat-update-icon'], value['cat-update-name'])
      .subscribe(
        (response) => {
          alert(`Category ${value['cat-update-icon']} was Updated`);
          window.location.reload();
        },
        (error) => {
          alert('Error while updating category');
          console.log('Error while updating category', error);
        }
      );
  }
  OnCancelClick(id: string) {
    const el = document.getElementById(`update-form-${id}`);
    el?.classList.add('hidden');
  }

  updateForm = new FormGroup({
    'cat-update-icon': new FormControl('', [
      Validators.required,
      Validators.pattern('^fa-[a-z]+(?:-[a-z]+)*$'),
    ]),
    'cat-update-name': new FormControl('', Validators.required),
  });

  onAddNewClick() {
    this.addIsEnabled = true;
  }
  handleAddIsEnabledChange(value: boolean) {
    this.addIsEnabled = value;
  }
}
