import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from './_auth/auth.guard';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { ShowAllProductsComponent } from './show-all-products/show-all-products.component';
import { ProductResolveService } from './_services/product-resolve.service';
import { ProductViewDetailComponent } from './product-view-detail/product-view-detail.component';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { BuyProductResolverService } from './_services/buy-product-resolver.service';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { RegisterComponent } from './register/register.component';
import { CartComponent } from './cart/cart.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';
import { OrderDetailsComponent } from './order-details/order-details.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'user', component: UserComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  {
    path: 'addUpdateProduct', component: AddNewProductComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] },
    resolve: {
      product: ProductResolveService
    }
  },
  { path: 'showAllProducts', component: ShowAllProductsComponent, canActivate: [AuthGuard], data: { roles: ['Admin'] } },
  { path: 'productViewDetail', component: ProductViewDetailComponent, resolve: { product: ProductResolveService } },
  {
    path: 'buyProduct', component: BuyProductComponent, canActivate: [AuthGuard], data: { roles: ['User'] },
    resolve: {
      productDetails: BuyProductResolverService
    }
  },
  { path: 'orderConfirm', component: OrderConfirmationComponent, canActivate: [AuthGuard], data: { roles: ['User'] } },
  {
    path: "cart",
    component: CartComponent,
    canActivate: [AuthGuard],
    data: { roles: ["User"] }
  },
  {
    path:"userOrders",
    component: UserOrdersComponent,
    canActivate: [AuthGuard],
    data: { roles: ["User"] }
  },
  {
    path: "orderInformation",
    component: OrderDetailsComponent,
    canActivate: [AuthGuard],
    data: { roles: ["Admin"] },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
