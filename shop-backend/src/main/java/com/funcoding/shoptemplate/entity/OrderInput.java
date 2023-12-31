package com.funcoding.shoptemplate.entity;

import java.util.List;

public class OrderInput {
    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String alternateContactNumber;
    private String transactionId;
    private List<OrderProductQuantity> orderProductQuantities;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAlternateContactNumber() {
        return alternateContactNumber;
    }

    public void setAlternateContactNumber(String alternateContactNumber) {
        this.alternateContactNumber = alternateContactNumber;
    }

    public List<OrderProductQuantity> getOrderProductQuantities() {
        return orderProductQuantities;
    }

    public void setOrderProductQuantities(List<OrderProductQuantity> orderProductQuantities) {
        this.orderProductQuantities = orderProductQuantities;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "OrderInput{" +
                "fullName='" + fullName + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", alternateContactNumber='" + alternateContactNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", orderProductQuantities=" + orderProductQuantities +
                '}';
    }
}
