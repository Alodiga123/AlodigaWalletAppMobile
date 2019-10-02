package com.example.c2paplicationmobile;

public class Money {

    public int productImage;
    public String productName;
    public String productPrice;
    public String productWeight;
    public String productQty;

    public Money(String productName, int productImage, String productPrice, String productWeight, String productQty) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
        this.productQty = productQty;
    }

    public Money() {

    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
