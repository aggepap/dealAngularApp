import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject, type OnInit } from '@angular/core';
import { initFlowbite } from 'flowbite';
import { LogoutAlertComponent } from '../../../shared/components/logout-alert/logout-alert.component';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [LogoutAlertComponent, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  //======================================================
  //  Inject Services
  //======================================================
  userService = inject(UsersService);

  //======================================================
  //  Properties
  //======================================================
  user = this.userService.user;
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
