package com.funcoding.shoptemplate.entity;

import javax.persistence.*;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @Column(name = "order_full_name")
    private String orderFullName;
    @Column(name = "order_full_order")
    private String orderFullOrder;
    @Column(name = "order_contact_number")
    private String orderContactNumber;
    @Column(name = "order_alternate_contact_number")
    private String orderAlternateContactNumber;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "order_amount")
    private Double orderAmount;
    @OneToOne
    private Product product;
    @OneToOne
    private User user;
    @Column(name = "transaction_id")
    private String transactionId;

    public OrderDetail() {

    }

    public OrderDetail(String orderFullName, String orderFullOrder, String orderContactNumber,
                       String orderAlternateContactNumber, String orderStatus, Double orderAmount,
                       Product product, User user, String transactionId) {
        this.orderFullName = orderFullName;
        this.orderFullOrder = orderFullOrder;
        this.orderContactNumber = orderContactNumber;
        this.orderAlternateContactNumber = orderAlternateContactNumber;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.product = product;
        this.user = user;
        this.transactionId = transactionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderFullName() {
        return orderFullName;
    }

    public void setOrderFullName(String orderFullName) {
        this.orderFullName = orderFullName;
    }

    public String getOrderFullOrder() {
        return orderFullOrder;
    }

    public void setOrderFullOrder(String orderFullOrder) {
        this.orderFullOrder = orderFullOrder;
    }

    public String getOrderContactNumber() {
        return orderContactNumber;
    }

    public void setOrderContactNumber(String orderContactNumber) {
        this.orderContactNumber = orderContactNumber;
    }

    public String getOrderAlternateContactNumber() {
        return orderAlternateContactNumber;
    }

    public void setOrderAlternateContactNumber(String orderAlternateContactNumber) {
        this.orderAlternateContactNumber = orderAlternateContactNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", orderFullName='" + orderFullName + '\'' +
                ", orderFullOrder='" + orderFullOrder + '\'' +
                ", orderContactNumber='" + orderContactNumber + '\'' +
                ", orderAlternateContactNumber='" + orderAlternateContactNumber + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
