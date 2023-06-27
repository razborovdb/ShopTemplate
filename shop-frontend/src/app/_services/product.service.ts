import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../_model/product.model';
import { UserAuthService } from '../_services/user-auth.service';
import { OrderDetails } from '../_model/order-details.model';
import { UserOrderDetails } from '../_model/order.model';
import { Observable } from 'rxjs';
import { StripeModel } from '../_model/stripe.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  PATH_OF_API = 'http://localhost:9090';

  requestHeader = new HttpHeaders({ 'Authorization': 'Bearer ' });

  constructor(private userAuthService: UserAuthService, private httpclient: HttpClient) { }

  public addProduct(product: FormData) {

    return this.httpclient.post<Product>(this.PATH_OF_API + '/product/add', product, {
      headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
    });
  }

  public getAllProducts(pageNumber, size, searchkeyword) {
    
    if (this.userAuthService.getToken()) {
      return this.httpclient.get<Product[]>(this.PATH_OF_API + '/products?pageNumber=' + pageNumber +'&size=' + size +
        '&searchKey=' + searchkeyword, {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get<Product[]>(this.PATH_OF_API + '/products?pageNumber=' + pageNumber +'&size=' + size+
        '&searchKey=' + searchkeyword, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
        
      });
    }
    
  }

  public deleteProduct(productId: number) {

    return this.httpclient.delete(this.PATH_OF_API + '/product/' + productId, {
      headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
    });
  }

  public getProduct(productId) {
    if (this.userAuthService.getToken()) {

      return this.httpclient.get<Product>(this.PATH_OF_API + '/product/' + productId, {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get<Product>(this.PATH_OF_API + '/product/' + productId, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public getProductDetails(isSingleProductCheckout,productId) {
    if (this.userAuthService.getToken()) {

      return this.httpclient.get<Product[]>(this.PATH_OF_API + '/product/details/' + isSingleProductCheckout + '/' + productId, {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get<Product[]>(this.PATH_OF_API + '/product/details/' + isSingleProductCheckout + '/' + productId, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public placeOrder(orderDetails: OrderDetails, isCartCheckout) {
    if (this.userAuthService.getToken()) {

      return this.httpclient.post(this.PATH_OF_API + '/order/' + isCartCheckout, orderDetails, {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.post(this.PATH_OF_API + '/order/' + isCartCheckout, orderDetails, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public addToCart(productId) {

    if (this.userAuthService.getToken()) {

      return this.httpclient.post(this.PATH_OF_API + '/addToCart/' + productId,  {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.post(this.PATH_OF_API + '/addToCart/' + productId, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public deleteCartItem(cartId) {
    if (this.userAuthService.getToken()) {

      return this.httpclient.delete(this.PATH_OF_API + '/deleteCartItem/' + cartId,  {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.delete(this.PATH_OF_API + '/deleteCartItem/' + cartId, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public getCartDetails() {
    if (this.userAuthService.getToken()) {

      return this.httpclient.get(this.PATH_OF_API + '/getCartDetails',  {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get(this.PATH_OF_API + '/getCartDetails', {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public getUserOrders(): Observable<UserOrderDetails[]> {
    if (this.userAuthService.getToken()) {

      return this.httpclient.get<UserOrderDetails[]>(this.PATH_OF_API + '/getOrderDetails',  {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get<UserOrderDetails[]>(this.PATH_OF_API + '/getOrderDetails', {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }

  }

  public createTransaction(orderDetails: OrderDetails, isCartCheckout, amount) {
    if (this.userAuthService.getToken()) {
      const resp = this.httpclient.post<StripeModel>(this.PATH_OF_API + '/createTransaction/' + amount + '/' + isCartCheckout, orderDetails, {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
      console.log("******************************* end");
      console.log(resp);
      return resp;
    } else {
      return this.httpclient.post<StripeModel>(this.PATH_OF_API + '/createTransaction/' + amount + '/' + isCartCheckout, orderDetails,{
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }

  }

  public markAsDelivered(orderId) {
    if (this.userAuthService.getToken()) {

      return this.httpclient.put(this.PATH_OF_API + '/markOrderAsDelivered/' + orderId,  {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.put(this.PATH_OF_API + '/markOrderAsDelivered/' + orderId, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }
  }

  public getAllOrderDetailsForAdmin(status: string): Observable<UserOrderDetails[]> {
    if (this.userAuthService.getToken()) {

      return this.httpclient.get<UserOrderDetails[]>(this.PATH_OF_API + '/getAllOrderDetails/' + status,  {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get<UserOrderDetails[]>(this.PATH_OF_API + '/getAllOrderDetails/' + status, {
        headers: new HttpHeaders({ 'No-Auth': 'True' }),
      });
    }

  }



}
