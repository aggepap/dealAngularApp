import { environment } from '@/src/environments/environment.development';
import { HttpClient, type HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable, signal, effect } from '@angular/core';

import type {
  AuthResponse,
  Creds,
  LoggedInUser,
  UserInsertDTO,
  UserReadOnlyDTO,
} from '../interfaces/users';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { ErrorService } from './error.service';

const apiUrl = environment.apiURL;
const STORES_API_URL = `${apiUrl}/users`;
const AUTH_API_URL = `${apiUrl}/auth`;

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  //======================================================
  //  Inject Services
  //======================================================
  http: HttpClient = inject(HttpClient);
  router = inject(Router);
  errorService = inject(ErrorService);
  //======================================================
  //  Properties
  //======================================================
  user = signal<LoggedInUser | null>(null);
  sessionExpired = signal<boolean>(false);

  constructor() {
    //check if user is logged in on page load
    const access_token = localStorage.getItem('access_token');
    if (access_token) {
      const decodedTokenData = JSON.parse(
        atob(access_token.split('.')[1])
      ) as LoggedInUser;
      this.user.set(decodedTokenData);
    }
    effect(() => {
      if (this.user()) {
        console.log('User Loggen in: ', this.user()?.sub);
      } else {
        console.log('No user logged in');
      }
    });
  }
  /**
   * Retrieves a user by their username from the backend API.
   *
   * @param username The username of the user to retrieve.
   * @returns Observable of a `UserReadOnlyDTO` object containing user data (read-only format).
   */
  getUserbyUsername(username: string) {
    return this.http.get<UserReadOnlyDTO>(
      `${STORES_API_URL}/find/username?=${username}`,
      {
        headers: {
          Accept: 'application/json',
        },
      }
    );
  }

  /**
   * Creates a new user on the backend API using FormData containing user data.
   *
   * @param formData An object containing user data in `UserInsertDTO` format.
   * @returns Observable of the newly created user data as `UserInsertDTO`.
   */
  addUser(formData: UserInsertDTO) {
    console.log(formData);
    return this.http
      .post<UserInsertDTO>(`${STORES_API_URL}/add`, formData)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          this.errorService.errorMessage.set('Error while creating user');
          this.errorService.errorColor.set('red');
          return throwError(() => error.error);
        })
      );
  }

  /**
   * Logs in a user using credentials on the backend API.
   *
   * @param credentials An object containing user credentials (`Creds` type).
   * @returns Observable of an `AuthResponse` object containing authentication information (e.g., token).
   */
  userLogin(credentials: Creds) {
    return this.http.post<AuthResponse>(`${AUTH_API_URL}/login`, credentials);
  }

  /**
   * Logs out the current user by clearing user data and access token.
   */
  logout() {
    this.user.set(null);
    localStorage.removeItem('access_token');
    this.errorService.errorMessage.set('You have been logged out succesfully');
    this.errorService.errorColor.set('green');
    this.router.navigate(['']);
  }
}
