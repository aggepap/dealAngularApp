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
import { AllUsersViewComponent } from './components/users/all-users-view/all-users-view.component';
import { adminGuard } from './shared/guards/admin.guard';
AddProductFormComponent;

export const routes: Routes = [
  { path: '', component: MainPageComponent, title: 'Deals Site Home Page' },
  {
    path: 'categories',
    component: AllCategoriesViewComponent,
    title: 'Deals Site Categories',
  },
  {
    path: 'categories/:id',
    component: SingleCategoryComponent,
    title: 'Deals Site Category',
  },
  {
    path: 'stores',
    component: AllStoreViewComponent,
    title: 'Deals Site Stores',
  },
  {
    path: 'deals',
    component: AllProductsViewComponent,
    title: 'Deals Site Deals',
  },
  {
    path: 'deals/add',
    component: AddProductFormComponent,
    canActivate: [authGuard],
    title: 'Add a new deal',
  },
  {
    path: 'deals/:id',
    component: SingleProductComponent,
    title: 'Deals Site Deal',
  },
  {
    path: 'register',
    component: UserRegistrationComponent,
    title: 'Create an account',
  },
  { path: 'login', component: UserLoginComponent, title: 'Deals Site Login' },
  {
    path: 'users',
    component: AllUsersViewComponent,
    canActivate: [adminGuard],
  },
];
