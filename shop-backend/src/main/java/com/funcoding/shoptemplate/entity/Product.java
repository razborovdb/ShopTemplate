package com.funcoding.shoptemplate.entity;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "product_discounted_price")
    private Double productDiscountedPrice;
    @Column(name = "product_actual_price")
    private Double productActualPrice;

    public Product() {

    }

    public Product(Long productId, String productName, String productDescription, Double productDiscountedPrice, Double productActualPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productDiscountedPrice = productDiscountedPrice;
        this.productActualPrice = productActualPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductDiscountedPrice() {
        return productDiscountedPrice;
    }

    public void setProductDiscountedPrice(Double productDiscountedPrice) {
        this.productDiscountedPrice = productDiscountedPrice;
    }

    public Double getProductActualPrice() {
        return productActualPrice;
    }

    public void setProductActualPrice(Double productActualPrice) {
        this.productActualPrice = productActualPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productDiscountedPrice=" + productDiscountedPrice +
                ", productActualPrice=" + productActualPrice +
                '}';
    }
}
