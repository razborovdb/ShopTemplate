import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../_model/product.model';
import { UserAuthService } from '../_services/user-auth.service';

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

  public getAllProducts() {
    
    if (this.userAuthService.getToken()) {
      return this.httpclient.get<Product[]>(this.PATH_OF_API + '/products', {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.userAuthService.getToken() }),
      });
    } else {
      return this.httpclient.get<Product[]>(this.PATH_OF_API + '/products', {
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
}
