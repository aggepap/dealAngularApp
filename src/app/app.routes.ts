import type { Routes } from '@angular/router';
import { AllCategoriesViewComponent } from './components/categories/all-categories-view/all-categories-view.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { AllStoreViewComponent } from './components/stores/all-store-view/all-store-view.component';
import { AddProductFormComponent } from './components/products/add-product-form/add-product-form.component';
import { AllProductsViewComponent } from './components/products/all-products-view/all-products-view.component';
import { SingleProductComponent } from './components/products/single-product/single-product.component';
import { SingleCategoryComponent } from './components/categories/single-category/single-category.component';
import { UserRegistrationComponent } from './components/users/user-registration/user-registration.component';
import { UserLoginComponent } from './components/users/user-login/user-login.component';
import { authGuard } from './shared/guards/auth.guard';
AddProductFormComponent;

export const routes: Routes = [
  { path: '', component: MainPageComponent },
  { path: 'categories', component: AllCategoriesViewComponent },
  { path: 'categories/:id', component: SingleCategoryComponent },
  { path: 'stores', component: AllStoreViewComponent },
  { path: 'deals', component: AllProductsViewComponent },
  {
    path: 'deals/add',
    component: AddProductFormComponent,
    canActivate: [authGuard],
  },
  { path: 'deals/:id', component: SingleProductComponent },
  { path: 'register', component: UserRegistrationComponent },
  { path: 'login', component: UserLoginComponent },
];
