import { Component } from '@angular/core';
import { DealCardsComponent } from '../deal-cards/deal-cards.component';
import { CategoriesSectionComponent } from '../categories-section/categories-section.component';
import { HeroComponent } from '../hero/hero.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    DealCardsComponent,
    CategoriesSectionComponent,
    HeroComponent,
    RouterLink,
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css',
})
export class MainPageComponent {}
