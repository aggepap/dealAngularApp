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

  //======================================================
  //  Properties
  //======================================================
  user = signal<LoggedInUser | null>(null);

  constructor() {
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

  addUser(formData: UserInsertDTO) {
    console.log(formData);
    return this.http
      .post<UserInsertDTO>(`${STORES_API_URL}/add`, formData)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          return throwError(() => error.error);
        })
      );
  }

  userLogin(credentials: Creds) {
    return this.http.post<AuthResponse>(`${AUTH_API_URL}/login`, credentials);
  }
  logout() {
    this.user.set(null);
    localStorage.removeItem('acccess_token');
    this.router.navigate(['']);
  }
}
