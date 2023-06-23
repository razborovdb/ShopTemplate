package com.funcoding.shoptemplate.controller;

import com.funcoding.shoptemplate.entity.ImageModel;
import com.funcoding.shoptemplate.entity.Product;
import com.funcoding.shoptemplate.entity.User;
import com.funcoding.shoptemplate.service.ProductService;
import com.funcoding.shoptemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/product/add"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart(name="imageFile", required = false) MultipartFile[] file) {
        try {
            if(file != null) {
                Set<ImageModel> imageModels = uploadImage(file);
                product.setProductImages(imageModels);
            } else {
                product.setProductImages(new HashSet<>());
            }
            return productService.addNewProduct(product);
        } catch (Exception e) {
            return null;
        }

    }

    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imagesModels = new HashSet<>();
        for(MultipartFile file: multipartFiles) {
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imagesModels.add(imageModel);
        }
        return imagesModels;
    }


    @GetMapping(value = {"/products"})
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = {"/product/{productId}"})
    public Product getProductById(@PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

}
