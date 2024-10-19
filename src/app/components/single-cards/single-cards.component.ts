import { Component, Input } from '@angular/core';
import type { Deals } from '../../shared/interfaces/deals';



@Component({
  selector: 'app-single-cards',
  standalone: true,
  imports: [],
  templateUrl: './single-cards.component.html',
  styleUrl: './single-cards.component.css'
})
export class SingleCardsComponent {
  @Input()
  deal!: Deals;
  

}
