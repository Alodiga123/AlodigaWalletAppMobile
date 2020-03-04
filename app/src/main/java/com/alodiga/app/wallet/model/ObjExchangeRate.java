package com.alodiga.app.wallet.model;

public class ObjExchangeRate {

                    private String amount;
                    private String beginingDate_nanos;
                    private ObjCountry country;
                    private ObjCurrency currency;
                    private String id;


    public ObjExchangeRate() {
    }

    public ObjExchangeRate(String amount, String beginingDate_nanos, ObjCountry country, ObjCurrency currency, String id) {
        this.amount = amount;
        this.beginingDate_nanos = beginingDate_nanos;
        this.country = country;
        this.currency = currency;
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBeginingDate_nanos() {
        return beginingDate_nanos;
    }

    public void setBeginingDate_nanos(String beginingDate_nanos) {
        this.beginingDate_nanos = beginingDate_nanos;
    }

    public ObjCountry getCountry() {
        return country;
    }

    public void setCountry(ObjCountry country) {
        this.country = country;
    }

    public ObjCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(ObjCurrency currency) {
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
