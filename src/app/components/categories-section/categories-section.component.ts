import { Component, Input } from '@angular/core';
import { DealCategories } from '../../shared/interfaces/deal-categories';

@Component({
  selector: 'app-categories-section',
  standalone: true,
  imports: [],
  templateUrl: './categories-section.component.html',
  styleUrl: './categories-section.component.css'
})
export class CategoriesSectionComponent {

  @Input() dealCategory: DealCategories | undefined;

}
