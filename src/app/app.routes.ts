import { Routes } from '@angular/router';
import { AllCategoriesViewComponent } from './components/categories-section/all-categories-view/all-categories-view.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { AllStoreViewComponent } from './components/stores/all-store-view/all-store-view.component';

export const routes: Routes = [
  { path: '', component: MainPageComponent },
  { path: 'categories', component: AllCategoriesViewComponent },
  { path: 'stores', component: AllStoreViewComponent },
];
