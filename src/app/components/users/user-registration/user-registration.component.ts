import type { UserInsertDTO } from '@/src/app/shared/interfaces/users';
import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject } from '@angular/core';
import {
  FormGroup,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.css',
})
export class UserRegistrationComponent {
  //==============================================
  //  Services injection
  //==============================================
  userService = inject(UsersService);
  user = this.userService.user;
  router = inject(Router);

  //==============================================
  //  Properties
  //==============================================
  passwordPattern = '^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$';
  passwordMatch = true;
  userExists = false;
  errorMessages: any = {};
  showSuccess = false;

  /**
   * Redirects the user to the home page if they are already logged in.
   */
  ngOnInit() {
    if (this.user()) {
      this.router.navigate(['/']);
    }
  }
  /**
   * FormGroup representing the user registration form.
   */
  registerForm = new FormGroup({
    username: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern(this.passwordPattern),
    ]),
    confirmPassword: new FormControl('', [
      Validators.required,
      Validators.pattern(this.passwordPattern),
    ]),
  });

  /**
   * Handles the user registration form submission.
   *
   * 1. Checks if the password and confirm password fields match.
   *    - If they match, proceeds with user registration.
   *    - If they don't match, sets the 'passwordMatch' flag to false to indicate an error.
   * 2. Creates a UserInsertDTO object with the form data (trimmed username and password).
   * 3. Calls the userService to add the new user.
   * 4. Subscribes to the observable returned by the service:
   *    - On success:
   *        - Clears any existing error messages.
   *        - Sets 'passwordMatch' to true (for informational purposes).
   *    - On error:
   *        - Sets the 'errorMessages' property with the error details received from the service.
   *    - On completion (regardless of success or error):
   *        - Sets 'showSuccess' to true to display a success message (potentially conditional based on error state).
   */
  onNewUserAdd(value: any) {
    this.passwordMatch = true;
    this.showSuccess = false;
    if (value.password === value.confirmPassword) {
      const userInsertDTO: UserInsertDTO = {
        username: value.username.trim(),
        password: value.password.trim(),
      };
      this.userService.addUser(userInsertDTO).subscribe({
        next: (data) => {
          this.errorMessages = {};
          this.passwordMatch = true;
        },
        error: (error) => {
          this.errorMessages = error;
        },
        complete: () => {
          this.showSuccess = true;
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 1000);
        },
      });
    } else {
      this.passwordMatch = false;
    }
  }
}
