import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { Dismiss } from 'flowbite';
import type { DismissOptions, DismissInterface } from 'flowbite';

@Component({
  selector: 'app-logout-alert',
  standalone: true,
  imports: [],
  templateUrl: './logout-alert.component.html',
  styleUrl: './logout-alert.component.css',
})
export class LogoutAlertComponent implements AfterViewInit {
  @ViewChild('alertBorder', { static: false }) alertBorder!: ElementRef;
  @ViewChild('buttonClose', { static: false }) buttonClose!: ElementRef;

  dismiss: DismissInterface | undefined; // Make dismiss optional

  ngAfterViewInit() {
    if (this.alertBorder && this.buttonClose) {
      const options: DismissOptions = {
        transition: 'transition-opacity',
        duration: 1000,
        timing: 'ease-out',
        onHide: (context, targetEl) => {
          console.log('Element has been dismissed');
          console.log(targetEl);
        },
      };

      this.dismiss = new Dismiss(
        this.alertBorder.nativeElement,
        this.buttonClose.nativeElement,
        options
      );

      // Programmatically hide if needed (e.g., based on a condition)
      // this.dismiss.hide();
    } else {
      console.error('Alert elements not found in the template.');
    }
  }

  hideAlert() {
    if (this.dismiss) {
      this.dismiss.hide();
    }
  }
}
