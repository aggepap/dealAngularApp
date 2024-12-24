import type {
  UserInsertDTO,
  UserRegisterFormData,
} from '@/src/app/shared/interfaces/users';
import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject } from '@angular/core';
import {
  FormGroup,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
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

  //==============================================
  //  Properties
  //==============================================
  passwordPattern = '^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$';
  passwordMatch = true;
  userExists = false;
  errorMessages: any = {};
  showSuccess = false;
  //==============================================
  //  Registration Form
  //==============================================
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

  //==============================================
  //  Add a new user
  //==============================================
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
        },
      });
    } else {
      this.passwordMatch = false;
    }
  }
}
