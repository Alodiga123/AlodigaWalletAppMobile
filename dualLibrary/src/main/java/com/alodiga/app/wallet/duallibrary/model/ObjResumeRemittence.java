package com.alodiga.app.wallet.duallibrary.model;

public class ObjResumeRemittence {

    private String amountToSendRemettence;
    private String receiverAmount;
    private String realAmountToSend;

    ObjExchangeRate exchangeRateDestiny;
    ObjExchangeRate  exchangeRateSource;
    ObjRatePaymentNetwork ratePaymentNetwork;

    public ObjResumeRemittence() {
    }

    public ObjResumeRemittence(String amountToSendRemettence, String receiverAmount, String realAmountToSend,  ObjExchangeRate exchangeRateDestiny, ObjExchangeRate exchangeRateSource, ObjRatePaymentNetwork ratePaymentNetwork) {
        this.amountToSendRemettence = amountToSendRemettence;
        this.receiverAmount = receiverAmount;
        this.realAmountToSend= realAmountToSend;
        this.exchangeRateDestiny = exchangeRateDestiny;
        this.exchangeRateSource = exchangeRateSource;
        this.ratePaymentNetwork = ratePaymentNetwork;
    }

    public String getRealAmountToSend() {
        return realAmountToSend;
    }

    public void setRealAmountToSend(String realAmountToSend) {
        this.realAmountToSend = realAmountToSend;
    }

    public String getAmountToSendRemettence() {
        return amountToSendRemettence;
    }

    public void setAmountToSendRemettence(String amountToSendRemettence) {
        this.amountToSendRemettence = amountToSendRemettence;
    }

    public String getReceiverAmount() {
        return receiverAmount;
    }

    public void setReceiverAmount(String receiverAmount) {
        this.receiverAmount = receiverAmount;
    }

    public ObjExchangeRate getExchangeRateDestiny() {
        return exchangeRateDestiny;
    }

    public void setExchangeRateDestiny(ObjExchangeRate exchangeRateDestiny) {
        this.exchangeRateDestiny = exchangeRateDestiny;
    }

    public ObjExchangeRate getExchangeRateSource() {
        return exchangeRateSource;
    }

    public void setExchangeRateSource(ObjExchangeRate exchangeRateSource) {
        this.exchangeRateSource = exchangeRateSource;
    }

    public ObjRatePaymentNetwork getRatePaymentNetwork() {
        return ratePaymentNetwork;
    }

    public void setRatePaymentNetwork(ObjRatePaymentNetwork ratePaymentNetwork) {
        this.ratePaymentNetwork = ratePaymentNetwork;
    }
}
