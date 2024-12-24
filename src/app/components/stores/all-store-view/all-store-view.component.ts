import { Component, inject } from '@angular/core';
import { StoreTabbedMenuComponent } from '../store-tabbed-menu/store-tabbed-menu.component';
import { AddStoreFormComponent } from '../add-store-form/add-store-form.component';
import { UsersService } from '@/src/app/shared/services/users.service';

@Component({
  selector: 'app-all-store-view',
  standalone: true,
  imports: [StoreTabbedMenuComponent, AddStoreFormComponent],
  templateUrl: './all-store-view.component.html',
  styleUrl: './all-store-view.component.css',
})
export class AllStoreViewComponent {
  userservice = inject(UsersService);
  user = this.userservice.user;
  addIsEnabled = false;

  /**
   * Handles the cancel event for the "add" form.
   * Sets the `addIsEnabled` flag based on the provided boolean value.
   *
   * @param value A boolean value indicating whether the "add" functionality should be enabled.
   */
  onCancelClick(value: boolean) {
    this.addIsEnabled = value;
  }

  /**
   * Handles the click event on an "add" button.
   * Toggles the `addIsEnabled` flag, which  controls the visibility of an "add" form or related UI elements.
   */
  onAddButtonClick() {
    this.addIsEnabled = !this.addIsEnabled;
  }
}
