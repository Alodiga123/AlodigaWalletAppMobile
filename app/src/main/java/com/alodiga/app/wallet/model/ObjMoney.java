package com.alodiga.app.wallet.model;

public class ObjMoney {

    public int productImage;
    public String productName;
    public String productPrice;
    public String productWeight;
    public String productQty;
    public boolean isTopup;
    public String productPriceEncrip;
    public String symbol;

    public ObjMoney(String productName, int productImage, String productPrice, String productWeight, String productQty) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
        this.productQty = productQty;
    }


    public ObjMoney(String productName, int productImage, String productPrice,String productPriceEncrip, String productWeight, String productQty,boolean isTopup, String symbol) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPriceEncrip = productPriceEncrip;
        this.productWeight = productWeight;
        this.productQty = productQty;
        this.isTopup=isTopup;
        this.symbol= symbol;
    }

    public ObjMoney(String productName, String productPrice, String productPriceEncrip) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPriceEncrip = productPriceEncrip;
    }

    public ObjMoney() {

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public boolean isTopup() {
        return isTopup;
    }

    public void setTopup(boolean topup) {
        isTopup = topup;
    }

    public String getProductPriceEncrip() {
        return productPriceEncrip;
    }

    public void setProductPriceEncrip(String productPriceEncrip) {
        this.productPriceEncrip = productPriceEncrip;
    }
}
