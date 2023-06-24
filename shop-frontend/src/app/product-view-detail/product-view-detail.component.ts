import { Component, Inject } from '@angular/core';
import { Product } from '../_model/product.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-view-detail',
  templateUrl: './product-view-detail.component.html',
  styleUrls: ['./product-view-detail.component.css']
})
export class ProductViewDetailComponent {
  selectedProductIndex = 0;
  product: Product;

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router) {}

  ngOnInit(): void { 
    this.product = this.activatedRoute.snapshot.data['product'];
  }

  changeIndex(i) {
    this.selectedProductIndex = i;
  }

  buyProduct(productId) {
    this.router.navigate(['/buyProduct', {
      isSingleProductCheckout: true,
      id: productId
    }]);
  }

}
