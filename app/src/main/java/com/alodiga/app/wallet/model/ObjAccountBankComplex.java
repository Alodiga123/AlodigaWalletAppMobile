package com.alodiga.app.wallet.model;


public class ObjAccountBankComplex {

    private String id;
    private String bankName;
    private String accountNumber;
    private String description;
    private String abaCode;
    private String bankId;
    private String shortName;

    public ObjAccountBankComplex(String id, String bankName, String accountNumber, String description, String abaCode,String bankId, String shortName) {
        this.id = id;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.description = description;
        this.abaCode = abaCode;
        this.bankId = bankId;
        this.shortName = shortName;
    }

    public ObjAccountBankComplex() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbaCode() {
        return abaCode;
    }

    public void setAbaCode(String abaCode) {
        this.abaCode = abaCode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
