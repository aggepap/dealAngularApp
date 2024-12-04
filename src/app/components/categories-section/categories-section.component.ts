import { Component, inject, Input } from '@angular/core';
import type { DealCategories } from '../../shared/interfaces/deal-categories';
import { CategoriesService } from '../../shared/services/categories.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-categories-section',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './categories-section.component.html',
  styleUrl: './categories-section.component.css',
})
export class CategoriesSectionComponent {
  @Input() dealCategory: DealCategories | undefined;

  categoriesService = inject(CategoriesService);
  categoriesList: DealCategories[] = [];

  ngOnInit() {
    let count = 0;
    this.categoriesService.getCategories().subscribe(
      (data: DealCategories[]) => {
        console.log(data);
        data.forEach((item) => {
          if (count < 12) {
            this.categoriesList.push(item);
            count++;
          }
        });
      },
      (error) => console.error('Error fetching categories', error)
    );
    console.log(this.categoriesList);
  }
}
