import { Injectable } from '@angular/core';
import { Product } from '../_model/product.model';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import {Observable, map, of} from 'rxjs';
import { ProductService } from './product.service';
import { ImageProcessingService } from './image-processing.service';

@Injectable({
  providedIn: 'root'
})
export class ProductResolveService implements Resolve<Product> {

  constructor(private productService: ProductService, 
    private imageProcessingService: ImageProcessingService) { }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> {
    const id = route.paramMap.get("productId");

    if (id) {
      return this.productService.getProduct(id)
      .pipe(
        map(p => this.imageProcessingService.createImages(p))
      );
    } else {
      return of(this.getProductDetails());
    }
  }

  getProductDetails() {
    return {
      productId: null,
      productName: "",
      productDescription: "",
      productDiscountedPrice: 0.0,
      productActualPrice: 0.0,
      productImages: []
    }
  }
}
