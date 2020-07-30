package com.alodiga.app.wallet.duallibrary.model;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjUserHasProduct {

    private String id;
    private String name;
    private String imageSource;
    private String currentBalance;
    private String symbol;
    private String isTopUp;
    private String numberCard;
    private String numberCardEncrip;

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

    public ObjUserHasProduct(String id, String name, String currentBalance, String symbol, String isTopup) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.currentBalance = currentBalance;
        this.isTopUp= isTopup;
    }

    public ObjUserHasProduct(String id, String name, String currentBalance, String symbol, String isTopup, String numberCard) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.currentBalance = currentBalance;
        this.isTopUp= isTopup;
        this.numberCard = numberCard;
    }


    public ObjUserHasProduct(String id, String name, String currentBalance, String symbol, String isTopup, String numberCard, String numberCardEncrip) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.currentBalance = currentBalance;
        this.isTopUp= isTopup;
        this.numberCard = numberCard;
        this.numberCardEncrip= numberCardEncrip;
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

    public String getIsTopUp() {
        return isTopUp;
    }

    public void setIsTopUp(String isTopUp) {
        this.isTopUp = isTopUp;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getNumberCardEncrip() {
        return numberCardEncrip;
    }

    public void setNumberCardEncrip(String numberCardEncrip) {
        this.numberCardEncrip = numberCardEncrip;
    }
}
