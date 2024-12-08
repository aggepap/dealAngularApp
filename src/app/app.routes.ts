import { Routes } from '@angular/router';
import { AllCategoriesViewComponent } from './components/categories-section/all-categories-view/all-categories-view.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { AllStoreViewComponent } from './components/stores/all-store-view/all-store-view.component';
import { AddProductFormComponent } from './components/products/add-product-form/add-product-form.component';
import { AllProductsViewComponent } from './components/products/all-products-view/all-products-view.component';
AddProductFormComponent;

export const routes: Routes = [
  { path: '', component: MainPageComponent },
  { path: 'categories', component: AllCategoriesViewComponent },
  { path: 'stores', component: AllStoreViewComponent },
  { path: 'products', component: AllProductsViewComponent },
  { path: 'products/add', component: AddProductFormComponent },
];
