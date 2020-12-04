package com.alodiga.app.wallet.model;


public class ObjAccountBank {

    private String id;
    private String name;
    private String accountNumber;


    public ObjAccountBank() {

    }

    public ObjAccountBank(String id, String name,String accountNumber) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
}
