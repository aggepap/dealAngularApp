import {
  type HttpErrorResponse,
  HttpEvent,
  type HttpHandler,
  type HttpInterceptor,
  type HttpRequest,
} from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { UsersService } from './users.service';

/**
 * @class AuthInterceptorService
 * @implements {HttpInterceptor}
 * @description Intercepts HTTP requests and adds an Authorization header with a Bearer token
 * if a token is present in local storage. Handles 401 (Unauthorized) and 403 (Forbidden)
 * errors by removing the token, setting sessionExpired flag and redirecting the user to the login page.
 */
@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  //======================================================
  //  Inject Services
  //======================================================
  router = inject(Router);
  userService = inject(UsersService);

  //======================================================
  //  Properties
  //======================================================
  user = this.userService.user;
  sessionExpired = this.userService.sessionExpired;

  /**
   * @method intercept
   * @param {HttpRequest<any>} req - The outgoing HTTP request.
   * @param {HttpHandler} next - The next interceptor in the chain.
   * @returns {Observable<HttpEvent<any>>} An Observable of the HTTP event.
   * @description Intercepts the HTTP request and adds the Authorization header if a token is present in local storage.
   * Handles 401/403 errors by removing the token, setting sessionExpired flag and redirecting to the login page.
   */

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authToken = localStorage.getItem('access_token');
    if (authToken) {
      const authRequest = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${authToken}`),
      });
      console.log('AUTH REQUEST', authRequest);
      return next.handle(authRequest).pipe(
        catchError((error: HttpErrorResponse) => {
          //checks if access_token has expired and redirects user to
          //login page, while showing session expired message
          if (error.status === 401 || error.status === 403) {
            localStorage.removeItem('access_token');
            this.user.set(null);
            this.sessionExpired.set(true);
            this.router.navigate(['/login']);
            return throwError(() => error);
          }
          return throwError(() => error);
        })
      );
    }

    return next.handle(req);
  }
}
