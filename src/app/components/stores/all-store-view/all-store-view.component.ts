import { Component } from '@angular/core';
import { StoreTabbedMenuComponent } from '../store-tabbed-menu/store-tabbed-menu.component';
import { AddStoreFormComponent } from '../add-store-form/add-store-form.component';

@Component({
  selector: 'app-all-store-view',
  standalone: true,
  imports: [StoreTabbedMenuComponent, AddStoreFormComponent],
  templateUrl: './all-store-view.component.html',
  styleUrl: './all-store-view.component.css',
})
export class AllStoreViewComponent {
  addIsEnabled = false;
  onCancelClick(value: boolean) {
    this.addIsEnabled = value;
  }
  onAddButtonClick() {
    this.addIsEnabled = !this.addIsEnabled;
  }
}
