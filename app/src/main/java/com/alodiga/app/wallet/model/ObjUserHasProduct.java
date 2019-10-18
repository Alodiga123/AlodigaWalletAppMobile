package com.alodiga.app.wallet.model;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjUserHasProduct {

    private String id;
    private String name;
    private String imageSource;
    private String currentBalance;
    private String symbol;

    public ObjUserHasProduct(String name) {
        this.name = name;
    }

    public ObjUserHasProduct(String id, String name) {
        this.name = name;
        this.id = id;
    }


    public ObjUserHasProduct(String id, String name, String currentBalance) {
        this.id = id;
        this.name = name;
        this.currentBalance = currentBalance;
    }

    public ObjUserHasProduct(String id, String name, String currentBalance, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.currentBalance = currentBalance;
    }

    public ObjUserHasProduct(String id, String name, String currentBalance, String symbol, String imageSource) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
        this.currentBalance = currentBalance;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
