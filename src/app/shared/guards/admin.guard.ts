import { type CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { UsersService } from '../services/users.service';

export const adminGuard: CanActivateFn = (route, state) => {
  //======================================================
  //  Inject Services
  //======================================================
  const usersService = inject(UsersService);
  const router = inject(Router);

  if (usersService.user()?.role === 'ADMIN') {
    return true;
  }
  return router.navigate(['/login']);
};