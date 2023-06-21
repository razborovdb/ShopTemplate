import { Component, OnInit } from '@angular/core';
import { Product } from '../_model/product.model';
import {NgForm} from '@angular/forms'
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-add-new-product',
  templateUrl: './add-new-product.component.html',
  styleUrls: ['./add-new-product.component.css']
})
export class AddNewProductComponent {
  product: Product = {
    productName: "",
    productDescription: "",
    productDiscountedPrice: 0.0,
    productActualPrice: 0.0
  }

  constructor(private productService: ProductService) {}

  ngOnInit(): void { }

  addProduct(productForm: NgForm) {
    this.productService.addProduct(this.product).subscribe(
      (response: Product) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
