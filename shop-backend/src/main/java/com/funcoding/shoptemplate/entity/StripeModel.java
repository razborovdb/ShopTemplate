package com.funcoding.shoptemplate.entity;

public class StripeModel {
    private String url;

    public StripeModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "StripeModel{" +
                "url='" + url + '\'' +
                '}';
    }
}
