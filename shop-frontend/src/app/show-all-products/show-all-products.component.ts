import { Component } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Product } from '../_model/product.model';
import { MatDialog } from '@angular/material/dialog';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';
import { ImageProcessingService } from '../_services/image-processing.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-all-products',
  templateUrl: './show-all-products.component.html',
  styleUrls: ['./show-all-products.component.css']
})
export class ShowAllProductsComponent {
  allProducts: Product[];
  displayedColumns: string[] = ['Id', 'Product Name', 'description', 'Product Discounted Price', 'Product Actual Price', 'Actions'];

  constructor(private productService: ProductService,
    public dialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private router: Router,
    private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.getAllProducts();
   }

  public getAllProducts() {

    this.productService.getAllProducts().subscribe(
      (response: Product[]) => {
        this.allProducts = response.map((product: Product) => this.imageProcessingService.createImages(product));
      },
      (error) => {
        console.log(error);
      }
    );

    
  }

  deleteProduct(productId) {
    this.productService.deleteProduct(productId).subscribe(
      (response) => {
        this.getAllProducts();
      },
      (error) => {
        console.log(error);
      }
    );

    
  }

  editProduct(productId) {
      this.router.navigate(['/addUpdateProduct', {productId: productId}]);
  }

  showProductImages(product: Product) {
    this.dialog.open(ShowProductImagesDialogComponent, {
      height: '400px',
      width: '800px',
      data: {
        images: product.productImages,
      },
    });
  }

    
}
