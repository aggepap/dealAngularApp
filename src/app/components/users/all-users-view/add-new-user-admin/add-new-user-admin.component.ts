import type { UserInsertDTO } from '@/src/app/shared/interfaces/users';
import { ErrorService } from '@/src/app/shared/services/error.service';
import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-new-user-admin',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './add-new-user-admin.component.html',
  styleUrl: './add-new-user-admin.component.css',
})
export class AddNewUserAdminComponent {
  userService = inject(UsersService);
  errorService = inject(ErrorService);
  router = inject(Router);
  passwordPattern = '^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$';
  errorMessages: any = {};

  addNewUserForm = new FormGroup({
    username: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern(this.passwordPattern),
    ]),
    role: new FormControl('USER', Validators.required),
  });

  addNewUser(value: any) {
    const userInsertDTO: UserInsertDTO = {
      username: value.username.trim(),
      password: value.password.trim(),
      role: value.role,
    };
    console.log(userInsertDTO);

    this.userService.addUser(userInsertDTO).subscribe({
      next: (data) => {},
      error: (error) => {
        this.errorMessages = error.error;
        this.errorService.errorMessage.set(this.errorMessages);
        this.errorService.errorColor.set('red');
      },
      complete: () => {
        this.errorService.errorMessage.set('User Added Successfully');
        this.errorService.errorColor.set('green');
        this.addNewUserForm.reset();
        setTimeout(() => {
          window.location.reload();
        }, 500);
      },
    });
  }
}
