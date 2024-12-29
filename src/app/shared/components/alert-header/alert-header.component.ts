import {
  Component,
  type AfterViewInit,
  type ElementRef,
  ViewChild,
  Input,
  inject,
} from '@angular/core';
import { Dismiss } from 'flowbite';
import type { DismissOptions, DismissInterface } from 'flowbite';
import { ErrorService } from '../../services/error.service';

@Component({
  selector: 'app-logout-alert',
  standalone: true,
  imports: [],
  templateUrl: './alert-header.component.html',
  styleUrl: './alert-header.component.css',
})
export class AlertHeaderComponent implements AfterViewInit {
  @ViewChild('alertBorder', { static: false }) alertBorder!: ElementRef;
  @ViewChild('buttonClose', { static: false }) buttonClose!: ElementRef;
  @Input() errorMessage = '';
  @Input() errorColor = '';
  errorService = inject(ErrorService);

  dismiss: DismissInterface | undefined;
  ngAfterViewInit() {
    if (this.alertBorder && this.buttonClose) {
      const options: DismissOptions = {
        transition: 'transition-opacity',
        duration: 1000,
        timing: 'ease-out',
        onHide: (context, targetEl) => {
          this.errorService.errorMessage.set(null);
          this.errorService.errorColor.set(null);
        },
      };

      this.dismiss = new Dismiss(
        this.alertBorder.nativeElement,
        this.buttonClose.nativeElement,
        options
      );
      setTimeout(() => {
        this.hideAlert();
      }, 5000);
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
