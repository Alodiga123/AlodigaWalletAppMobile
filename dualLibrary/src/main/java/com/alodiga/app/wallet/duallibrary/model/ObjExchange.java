package com.alodiga.app.wallet.duallibrary.model;

public class ObjExchange {

    private  String exange_amountCommission;
    private  String exange_valueCommission;
    private  String exange_totalDebit;
    private  String exange_amountConversion;
    private  String exange_exchangeRateProductSource;
    private  String exange_exchangeRateProductDestination;
    private  ObjTransferMoney exange_productSource;
    private  ObjTransferMoney exange_productDestination;
    private  String exange_includedAmount;
    private  String amountExchange;
    private  String exange_isPercentCommision;

    public ObjExchange(String exange_amountCommission, String exange_valueCommission, String exange_totalDebit, String exange_amountConversion, String exange_exchangeRateProductSource, String exange_exchangeRateProductDestination, ObjTransferMoney exange_productSource, ObjTransferMoney exange_productDestination, String exange_includedAmount, String amountExchange) {
        this.exange_amountCommission = exange_amountCommission;
        this.exange_valueCommission = exange_valueCommission;
        this.exange_totalDebit = exange_totalDebit;
        this.exange_amountConversion = exange_amountConversion;
        this.exange_exchangeRateProductSource = exange_exchangeRateProductSource;
        this.exange_exchangeRateProductDestination = exange_exchangeRateProductDestination;
        this.exange_productSource = exange_productSource;
        this.exange_productDestination = exange_productDestination;
        this.exange_includedAmount = exange_includedAmount;
        this.amountExchange = amountExchange;
    }

    public ObjExchange() {
    }

    public String getExange_amountCommission() {
        return exange_amountCommission;
    }

    public void setExange_amountCommission(String exange_amountCommission) {
        this.exange_amountCommission = exange_amountCommission;
    }

    public String getExange_valueCommission() {
        return exange_valueCommission;
    }

    public void setExange_valueCommission(String exange_valueCommission) {
        this.exange_valueCommission = exange_valueCommission;
    }

    public String getExange_totalDebit() {
        return exange_totalDebit;
    }

    public void setExange_totalDebit(String exange_totalDebit) {
        this.exange_totalDebit = exange_totalDebit;
    }

    public String getExange_amountConversion() {
        return exange_amountConversion;
    }

    public void setExange_amountConversion(String exange_amountConversion) {
        this.exange_amountConversion = exange_amountConversion;
    }

    public String getExange_exchangeRateProductSource() {
        return exange_exchangeRateProductSource;
    }

    public void setExange_exchangeRateProductSource(String exange_exchangeRateProductSource) {
        this.exange_exchangeRateProductSource = exange_exchangeRateProductSource;
    }

    public String getExange_exchangeRateProductDestination() {
        return exange_exchangeRateProductDestination;
    }

    public void setExange_exchangeRateProductDestination(String exange_exchangeRateProductDestination) {
        this.exange_exchangeRateProductDestination = exange_exchangeRateProductDestination;
    }

    public ObjTransferMoney getExange_productSource() {
        return exange_productSource;
    }

    public void setExange_productSource(ObjTransferMoney exange_productSource) {
        this.exange_productSource = exange_productSource;
    }

    public ObjTransferMoney getExange_productDestination() {
        return exange_productDestination;
    }

    public void setExange_productDestination(ObjTransferMoney exange_productDestination) {
        this.exange_productDestination = exange_productDestination;
    }

    public String getExange_includedAmount() {
        return exange_includedAmount;
    }

    public void setExange_includedAmount(String exange_includedAmount) {
        this.exange_includedAmount = exange_includedAmount;
    }

    public String getAmountExchange() {
        return amountExchange;
    }

    public void setAmountExchange(String amountExchange) {
        this.amountExchange = amountExchange;
    }

    public String getExange_isPercentCommision() {
        return exange_isPercentCommision;
    }

    public void setExange_isPercentCommision(String exange_isPercentCommision) {
        this.exange_isPercentCommision = exange_isPercentCommision;
    }
}

