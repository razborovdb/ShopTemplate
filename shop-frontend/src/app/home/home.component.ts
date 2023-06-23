import { Component, OnInit } from '@angular/core';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { ImageProcessingService } from '../_services/image-processing.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  allProduct: Product[];

  constructor(private productService: ProductService,
    public dialog: MatDialog,
    private imageProcessingService: ImageProcessingService
  ) { }

  ngOnInit(): void {
    this.getAllProducts();
  }


  public getAllProducts() {

    this.productService.getAllProducts().subscribe(
      (response: Product[]) => {
        this.allProduct = response.map((product: Product) => this.imageProcessingService.createImages(product));
        
      },
      (error) => {
        console.log(error);
      }
    );


  }



}
