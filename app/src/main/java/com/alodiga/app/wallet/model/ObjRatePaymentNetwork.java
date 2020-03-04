package com.alodiga.app.wallet.model;

public class ObjRatePaymentNetwork {

    private String amount;
    private String beginingDate_nanos;
    private String id;
    private ObjPaymentNetwork paymentNetwork;

    public ObjRatePaymentNetwork(String amount, String beginingDate_nanos, String id, ObjPaymentNetwork paymentNetwork) {
        this.amount = amount;
        this.beginingDate_nanos = beginingDate_nanos;
        this.id = id;
        this.paymentNetwork = paymentNetwork;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjPaymentNetwork getPaymentNetwork() {
        return paymentNetwork;
    }

    public void setPaymentNetwork(ObjPaymentNetwork paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }
}
