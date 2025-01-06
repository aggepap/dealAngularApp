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
import { ErrorService } from '@/src/app/shared/services/error.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css',
})
export class UserLoginComponent {
  //======================================================
  //  Inject Services
  //======================================================
  userService = inject(UsersService);
  errorService = inject(ErrorService);
  location = inject(Location);
  router = inject(Router);
  //======================================================
  //  Properties
  //======================================================
  sessionExpired = this.userService.sessionExpired;
  loginError = '';
  user = this.userService.user;

  /**
   * FormGroup representing the login form.
   */
  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  ngOnInit() {
    // Check if the user is already logged in
    if (this.user()) {
      this.router.navigate(['/']);
    }
  }

  /**
   * Handles the login form submission.
   *
   * 1. Retrieves the form values (username and password) from the login form.
   * 2. Calls the userLogin service to authenticate the user with the provided credentials.
   * 3. Subscribes to the observable returned by the service:
   *    - On success:
   *        - Extracts the access token from the response data.
   *        - Stores the token in local storage.
   *        - Decodes the JWT token to obtain user information (subject, role).
   *        - Updates the user service with the decoded user data.
   *        - Navigates back to the previous page using the Location service.
   *    - On error:
   *        - Logs the error to the console.
   *        - Sets the login error message to 'Invalid username or password'.
   *        - Sets an error on the login form for visual feedback.
   */
  onLogin() {
    const credentials = this.loginForm.value as Creds;
    if (credentials.email) {
      credentials.email = credentials.email.trim().toLowerCase();
    }
    this.userService.userLogin(credentials).subscribe({
      next: (data) => {
        const token = data.token;
        localStorage.setItem('access_token', token);
        const decodedTokenData = jwtDecode(token) as unknown as LoggedInUser;
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

        this.errorService.errorMessage.set(error.error.description);
        this.errorService.errorColor.set('red');
      },
    });
  }
}
