import { Component, Injector, NgZone } from '@angular/core';
import { NgForm } from '@angular/forms';
import { OrderDetails } from '../_model/order-details.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

declare var Razorpay: any;
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
    transactionId: '',
    orderProductQuantities: []
  }

  productDetails: Product[] = [];
  isSingleProductCheckout: string = '';

  constructor(private activatedRoute: ActivatedRoute,
    private productService: ProductService, private router: Router, private injector: Injector) {}

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

        // const ngZone = this.injector.get(NgZone);
        // ngZone.run(
        //   () => {
        //     this.router.navigate(["/orderConfirm"]);
        //   }
        // );
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

  // createTransactionAndPlaceOrder(orderForm: NgForm) {
  //   let amount = this.getTotal();
  //   this.productService.createTransaction(amount).subscribe(
  //     (response) => {
  //       console.log(response);
  //       this.openTransactioModal(response, orderForm);
  //     },
  //     (error) => {
  //       console.log(error);
  //     }
  //   );

  // }

  // openTransactioModal(response: any, orderForm: NgForm) {
  //   var options = {
  //     order_id: response.orderId,
  //     key: response.key,
  //     amount: response.amount,
  //     currency: response.currency,
  //     name: 'Learn programming yourself',
  //     description: 'Payment of online shopping',
  //     image: 'https://cdn.pixabay.com/photo/2023/01/22/13/46/swans-7736415_640.jpg',
  //     handler: (response: any) => {
  //       if(response!= null && response.razorpay_payment_id != null) {
  //         this.processResponse(response, orderForm);
  //       } else {
  //         alert("Payment failed..")
  //       }
       
  //     },
  //     prefill : {
  //       name:'LPY',
  //       email: 'LPY@GMAIL.COM',
  //       contact: '90909090'
  //     },
  //     notes: {
  //       address: 'Online Shopping'
  //     },
  //     theme: {
  //       color: '#F37254'
  //     }
  //   };

  //   var razorPayObject = new Razorpay(options);
  //   razorPayObject.open();
  // }

  // processResponse(resp: any, orderForm:NgForm) {
  //   this.orderDetails.transactionId = resp.razorpay_payment_id;
  //   this.placeOrder(orderForm);
  // }
}
