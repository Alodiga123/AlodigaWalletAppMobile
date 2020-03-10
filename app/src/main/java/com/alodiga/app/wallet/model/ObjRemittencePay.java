package com.alodiga.app.wallet.model;

public class ObjRemittencePay {

    private ObjTransferMoney reloadcard_source;
    private boolean rate_included;
    private ObjGenericObject destination_country;
    private ObjGenericObject Correspondent;
    private ObjGenericObject delivery_method;
    private String amount_;

    private String exchange_rate;
    private String shipping_rate;
    private String actual_amount_to_send;
    private String actual_amount_to_pay;
    private String total_to_pay;

    public ObjRemittencePay() {
    }

    public ObjRemittencePay(ObjTransferMoney reloadcard_source, boolean rate_included, ObjGenericObject destination_country, ObjGenericObject correspondent, ObjGenericObject delivery_method, String amount_, String exchange_rate, String shipping_rate, String actual_amount_to_send, String actual_amount_to_pay, String total_to_pay) {
        this.reloadcard_source = reloadcard_source;
        this.rate_included = rate_included;
        this.destination_country = destination_country;
        Correspondent = correspondent;
        this.delivery_method = delivery_method;
        this.amount_ = amount_;
        this.exchange_rate = exchange_rate;
        this.shipping_rate = shipping_rate;
        this.actual_amount_to_send = actual_amount_to_send;
        this.actual_amount_to_pay = actual_amount_to_pay;
        this.total_to_pay = total_to_pay;
    }

    public ObjTransferMoney getReloadcard_source() {
        return reloadcard_source;
    }

    public void setReloadcard_source(ObjTransferMoney reloadcard_source) {
        this.reloadcard_source = reloadcard_source;
    }

    public boolean getRate_included() {
        return rate_included;
    }

    public void setRate_included(boolean rate_included) {
        this.rate_included = rate_included;
    }

    public ObjGenericObject getDestination_country() {
        return destination_country;
    }

    public void setDestination_country(ObjGenericObject destination_country) {
        this.destination_country = destination_country;
    }

    public ObjGenericObject getCorrespondent() {
        return Correspondent;
    }

    public void setCorrespondent(ObjGenericObject correspondent) {
        Correspondent = correspondent;
    }

    public ObjGenericObject getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(ObjGenericObject delivery_method) {
        this.delivery_method = delivery_method;
    }

    public String getAmount_() {
        return amount_;
    }

    public void setAmount_(String amount_) {
        this.amount_ = amount_;
    }

    public String getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(String exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public String getShipping_rate() {
        return shipping_rate;
    }

    public void setShipping_rate(String shipping_rate) {
        this.shipping_rate = shipping_rate;
    }

    public String getActual_amount_to_send() {
        return actual_amount_to_send;
    }

    public void setActual_amount_to_send(String actual_amount_to_send) {
        this.actual_amount_to_send = actual_amount_to_send;
    }

    public String getActual_amount_to_pay() {
        return actual_amount_to_pay;
    }

    public void setActual_amount_to_pay(String actual_amount_to_pay) {
        this.actual_amount_to_pay = actual_amount_to_pay;
    }

    public String getTotal_to_pay() {
        return total_to_pay;
    }

    public void setTotal_to_pay(String total_to_pay) {
        this.total_to_pay = total_to_pay;
    }
}
