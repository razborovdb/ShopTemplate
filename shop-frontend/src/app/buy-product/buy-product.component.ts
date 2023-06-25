import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { OrderDetails } from '../_model/order-details.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent {

  orderDetails: OrderDetails = {
    fullName: '',
    fullAddress: '',
    contactNumber: '',
    alternateContactNumber: '',
    orderProductQuantities: []
  }

  productDetails: Product[] = [];
  isSingleProductCheckout: string = '';

  constructor(private activatedRoute: ActivatedRoute,
    private productService: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.productDetails = this.activatedRoute.snapshot.data['productDetails'];
    this.isSingleProductCheckout = this.activatedRoute.snapshot.paramMap.get("isSingleProductCheckout");
    this.productDetails.forEach(
      x => this.orderDetails.orderProductQuantities.push(
        {productId: x.productId, quantity: 1}
      )
    )
  }
  public placeOrder(orderForm: NgForm) {
    this.productService.placeOrder(this.orderDetails, this.isSingleProductCheckout).subscribe(
      (response) => {
        orderForm.reset();
        this.router.navigate(["/orderConfirm"]);
      },
      (err) => {
        console.log(err);
      }
    );
  }
  getQuantityForProduct(productId: number) {
    const product = this.orderDetails.orderProductQuantities.filter(
      (productQuantity) => productQuantity.productId === productId
    );

    return product[0].quantity;
  }

  getCalculatedTotal(productId, productDiscountedPrice) {
    const product = this.orderDetails.orderProductQuantities.filter(
      (productQuantity) => productQuantity.productId === productId
    );

    return product[0].quantity * productDiscountedPrice;
  }

  onQuantityChange(quantity, productId) {
    this.orderDetails.orderProductQuantities.filter(
      (productQuantity) => productQuantity.productId === productId
    )[0].quantity = quantity;

  }
  getTotal() {
    let total = 0;
    this.orderDetails.orderProductQuantities.forEach(
      (productQuantity) => {
        const price = this.productDetails.filter(product => product.productId === productQuantity.productId)[0].productDiscountedPrice;
        total += price * productQuantity.quantity;

      }
    );

    return total;
  }
}
