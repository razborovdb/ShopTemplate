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

  allProducts: Product[] = [];
  pageNumber: number = 0;
  size: number = 3;
  showButton = false;
  showTable = false;
  displayedColumns: string[] = ['Id', 'Product Name', 'description', 'Product Discounted Price', 'Product Actual Price', 'Actions'];

  constructor(private productService: ProductService,
    public dialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private router: Router,
    private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getAllProducts();
  }

  searchByKeyword(searchkeyword) {
    console.log(searchkeyword);
    this.pageNumber = 0;
    this.allProducts = [];
    this.getAllProducts(searchkeyword);
  }


  public getAllProducts(searchkeyword: string = "") {
    this.showTable = false;
    this.productService.getAllProducts(this.pageNumber, this.size, searchkeyword).subscribe(
      (response: Product[]) => {
        if (response.length == this.size) {
          this.showButton = true;
        } else {
          this.showButton = false;
        }
        this.showTable = true;
        response.map((product: Product) => this.imageProcessingService.createImages(product))
          .forEach(p => this.allProducts.push(p));
      },
      (error) => {
        this.showTable = true;
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
    this.router.navigate(['/addUpdateProduct', { productId: productId }]);
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

  loadNext() {
    this.pageNumber++;
    this.getAllProducts();
  }


}
