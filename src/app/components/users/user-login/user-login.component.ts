import type { Creds, LoggedInUser } from '@/src/app/shared/interfaces/users';
import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import {
  ReactiveFormsModule,
  Validators,
  FormGroup,
  FormControl,
} from '@angular/forms';
import { jwtDecode } from 'jwt-decode';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css',
})
export class UserLoginComponent {
  userService = inject(UsersService);
  location = inject(Location);

  loginError = '';

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  onLogin() {
    const credentials = this.loginForm.value as Creds;
    this.userService.userLogin(credentials).subscribe({
      next: (data) => {
        const token = data.token;
        localStorage.setItem('access_token', token);

        const decodedTokenData = jwtDecode(token) as unknown as LoggedInUser;

        console.log(decodedTokenData);

        this.userService.user.set({
          sub: decodedTokenData.sub,
          role: decodedTokenData.role,
        });

        this.location.back();
      },
      error: (error) => {
        console.log('Login Error', error);
        this.loginError = 'Invalid username or password';
        this.loginForm.setErrors({
          loginError: true,
        });
      },
    });
  }
}
