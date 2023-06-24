import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Product } from '../_model/product.model';
import { ProductService } from './product.service';
import { ImageProcessingService } from './image-processing.service';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class BuyProductResolverService implements Resolve<Product[]> {

  constructor(private productService: ProductService, 
    private imagePorocesssingService: ImageProcessingService) { }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Product[] | Observable<Product[]> | Promise<Product[]> {
    const id = route.paramMap.get("id")
    const isSingleProductCheckout = route.paramMap.get("isSingleProductCheckout")

    console.log("----------------------------");
    console.log(id);
    console.log(isSingleProductCheckout);

    const productDetails = this.productService.getProductDetails(isSingleProductCheckout, id)
    .pipe(
      map(
        (x:Product[], i) => x.map((product: Product) => this.imagePorocesssingService.createImages(product))
      )
    );
    
    console.log("----------------------------");
    console.log(productDetails);

    return productDetails;
  }


}
