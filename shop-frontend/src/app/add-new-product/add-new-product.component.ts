import { Component, OnInit } from '@angular/core';
import { Product } from '../_model/product.model';
import {NgForm} from '@angular/forms'
import { ProductService } from '../_services/product.service';
import { FileHandle } from '../_model/file-handle.model';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-new-product',
  templateUrl: './add-new-product.component.html',
  styleUrls: ['./add-new-product.component.css']
})
export class AddNewProductComponent {
  isNewProduct = true;
  product: Product = {
    productId: null,
    productName: "",
    productDescription: "",
    productDiscountedPrice: 0.0,
    productActualPrice: 0.0,
    productImages: []
  }
  

  constructor(private productService: ProductService,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void { 
    this.product = this.activatedRoute.snapshot.data['product'];
    if (this.product && this.product.productId) {
      this.isNewProduct = false;
    } else {
      this.isNewProduct = true;
    }
  }

  onFileSelected(event) {
    if(event.target.files) {
      const file = event.target.files[0];

      const fileHandle: FileHandle = {
        file: file,
        url: this.sanitizer.bypassSecurityTrustResourceUrl(
          window.URL.createObjectURL(file)
        )
      }
      this.product.productImages.push(fileHandle)
    }
  }

  addProduct(productForm: NgForm) {
    const productFormData = this.prepareFormData(this.product);
    this.productService.addProduct(productFormData).subscribe(
      (response: Product) => {
        if (this.isNewProduct) {
          productForm.reset();
          this.product.productImages = [];
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  clearForm(productForm: NgForm) {
    if (this.isNewProduct) {
      productForm.reset();
      this.product.productImages = [];
    }
  }

  prepareFormData(product: Product): FormData {
    const formData = new FormData();
    formData.append(
      'product',
      new Blob([JSON.stringify(product)],{type: 'application/json'})
    );
    for(var i = 0; i < product.productImages.length; i++) {
      formData.append(
        'imageFile',
        product.productImages[i].file,
        product.productImages[i].file.name
      );
    }
    return formData;
  }
  removeImages(i: number) {
    this.product.productImages.splice(i, 1)
  }
  fileDropped(fileHandle: FileHandle) {
    this.product.productImages.push(fileHandle);
  }
}
