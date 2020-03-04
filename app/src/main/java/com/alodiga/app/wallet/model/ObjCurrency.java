package com.alodiga.app.wallet.model;

public class ObjCurrency {

    private String id;
    private String iso;
    private String name;
    private String symbol;

    public ObjCurrency() {
    }

    public ObjCurrency(String id, String iso, String name, String symbol) {
        this.id = id;
        this.iso = iso;
        this.name = name;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
