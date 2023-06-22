import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../_model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  PATH_OF_API = 'http://localhost:9090';

  requestHeader = new HttpHeaders({ 'No-Auth': 'True' });

  constructor(private httpclient: HttpClient) { }

  public addProduct(product: FormData) {
    console.log("------------------------------------------")
    console.log(product);
    return this.httpclient.post<Product>(this.PATH_OF_API + '/product/add', product, {
      headers: this.requestHeader,
    });
  }
}
