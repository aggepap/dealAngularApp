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
  //==============================================================================
  // Inputs and Outputs
  //==============================================================================
  @Input() categoryList: any = [];
  @Input() storesList: any = [];
  @Output() searchFilter = new EventEmitter<(string | number)[]>();

  //==============================================================================
  //Search Form
  //==============================================================================
  mainPageSearchForm = new FormGroup({
    stores: new FormControl('-1'),
    categories: new FormControl('-1'),
    searchText: new FormControl(''),
  });

  /**
   * Generates a search filter array based on the provided form values.
   * This function takes form values related to stores, categories, and search text,
   * and constructs an array to be emitted as a search filter.
   *
   * @param value An object containing the form values. Expected properties are:
   *   - `stores`: The selected store(s) (can be a single value or an array).
   *   - `categories`: The selected category(ies) (can be a single value or an array).
   *   - `searchText`: The text entered in the search field.
   *
   * @emits searchFilter Emits an array representing the search filter. The array structure is:
   *   - Index 0: Selected store(s) (or empty string if none selected).
   *   - Index 1: Selected category(ies) (or empty string if none selected).
   *   - Index 2: Search text (or empty string if none entered).
   */
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
