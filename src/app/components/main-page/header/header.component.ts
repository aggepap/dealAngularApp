import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject, type OnInit } from '@angular/core';
import { initFlowbite } from 'flowbite';
import { AlertHeaderComponent } from '@/src/app/shared/components/alert-header/alert-header.component';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { ErrorService } from '@/src/app/shared/services/error.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [AlertHeaderComponent, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  //======================================================
  //  Inject Services
  //======================================================
  userService = inject(UsersService);
  errorService = inject(ErrorService);

  //======================================================
  //  Properties
  //======================================================
  user = this.userService.user;
  errorMessage = this.errorService.errorMessage;
  errorColor = this.errorService.errorColor;
  loggedOut = false;

  ngOnInit(): void {
    initFlowbite();
  }
  //logs out user and reloads page
  onLogout() {
    this.userService.logout();
    this.loggedOut = true;
  }
}
