import { Component, Input, inject } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-error-message',
  standalone: true,
  templateUrl: './error-message.component.html',
})
export class ErrorMessageComponent {
  //==============================================================================
  //Service injections / Inputs
  //==============================================================================
  @Input() message = 'An error occurred. Please try again.';
  private location = inject(Location);

  goBack() {
    this.location.back();
  }
}
