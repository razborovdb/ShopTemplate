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
import java.util.Set;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/product/add"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<ImageModel> imageModels = uploadImage(file);
            product.setProductImages(imageModels);
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

}
