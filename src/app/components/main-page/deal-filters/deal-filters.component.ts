import { Component, EventEmitter, Input, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-deal-filters',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './deal-filters.component.html',
  styleUrl: './deal-filters.component.css',
})
export class DealFiltersComponent {
  @Input() categoryList: any = [];
  @Input() storesList: any = [];
  @Output() searchFilter = new EventEmitter<(string | number)[]>();
  //Search Form
  //==============================================================================
  mainPageSearchForm = new FormGroup({
    stores: new FormControl('-1'),
    categories: new FormControl('-1'),
    searchText: new FormControl(''),
  });

  generateSearchFilter(value: any) {
    const emittedArray: any[] = [];
    if (value.stores) {
      emittedArray.push(value.stores);
    }
    if (value.categories) {
      emittedArray.push(value.categories);
    } else {
      emittedArray.push('');
    }

    if (value.searchText) {
      emittedArray.push(value.searchText);
    } else {
      emittedArray.push('');
    }

    this.searchFilter.emit(emittedArray);
  }
}
