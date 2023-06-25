import { Component, OnInit } from '@angular/core';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { ImageProcessingService } from '../_services/image-processing.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  pageNumber: number = 0;
  size: number = 3;
  allProduct: Product[] = [];
  showButton = false;

  constructor(private productService: ProductService,
    public dialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getAllProducts();
  }


  public getAllProducts() {

    this.productService.getAllProducts(this.pageNumber, this.size).subscribe(
      (response: Product[]) => {
        if(response.length==this.size) {
          this.showButton = true;
        } else {
          this.showButton = false;
        }
        response.map((product: Product) => this.imageProcessingService.createImages(product))
        .forEach(p => this.allProduct.push(p));
      },
      (error) => {
        console.log(error);
      }
    );
  }

  showProductDetails(productId) {
    this.router.navigate(['/productViewDetail', {productId: productId}]);
  }

  loadNext() {
    this.pageNumber++;
    this.getAllProducts();
  }

}
