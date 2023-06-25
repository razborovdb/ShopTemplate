import { Component } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { UserOrderDetails } from '../_model/order.model';

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.css']
})
export class UserOrdersComponent {
  displayedColumns = ["Name", "Address", "Contact No.", "Amount", "Status"];

  userOrderDetails: UserOrderDetails[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails() {
    this.productService.getUserOrders().subscribe(
      (resp: UserOrderDetails[]) => {
        console.log(resp);
        this.userOrderDetails = resp;
      }, (err)=> {
        console.log(err);
      }
    );
  }
}
