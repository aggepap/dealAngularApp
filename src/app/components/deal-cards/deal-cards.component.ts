import { Component, Input } from '@angular/core';
import { SingleCardsComponent } from '../single-cards/single-cards.component';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-deal-cards',
  standalone: true,
  imports: [SingleCardsComponent, CommonModule],
  templateUrl: './deal-cards.component.html',
  styleUrl: './deal-cards.component.css',
})
export class DealCardsComponent {}
