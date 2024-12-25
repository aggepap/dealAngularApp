import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  errorMessage = signal<string | null>(null);
  errorColor = signal<string | null>(null);
}
