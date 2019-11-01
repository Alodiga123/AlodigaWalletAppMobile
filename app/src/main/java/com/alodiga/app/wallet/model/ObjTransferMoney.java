package com.alodiga.app.wallet.model;

/**
 * Created by anyeli on 19/09/17.
 */

public class ObjTransferMoney {

    private String id;
    private String name;
    private String currency;
    private String symbol;



    public ObjTransferMoney(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjTransferMoney(String id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public ObjTransferMoney(String id, String name, String currency, String symbol) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.symbol = symbol;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
