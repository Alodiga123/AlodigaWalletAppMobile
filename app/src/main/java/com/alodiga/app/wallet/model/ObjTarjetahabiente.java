package com.alodiga.app.wallet.model;

public class ObjTarjetahabiente {

    private ObjPaymentInfo cardInfo;
    private String country;
    private String state;
    private String county;
    private String city;
    private String direction;
    private String zip_code;
    private Boolean isSave;

    private ObjTransferMoney product;
    private String amount;


    public ObjTarjetahabiente() {
    }

    public ObjTarjetahabiente(ObjPaymentInfo cardInfo, String country, String state, String county, String city, String direction, String zip_code, Boolean isSave, ObjTransferMoney product, String amount) {
        this.cardInfo = cardInfo;
        this.country = country;
        this.state = state;
        this.county = county;
        this.city = city;
        this.direction = direction;
        this.zip_code = zip_code;
        this.isSave = isSave;
        this.product = product;
        this.amount = amount;
    }

    public ObjPaymentInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(ObjPaymentInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public Boolean getSave() {
        return isSave;
    }

    public void setSave(Boolean save) {
        isSave = save;
    }

    public ObjTransferMoney getProduct() {
        return product;
    }

    public void setProduct(ObjTransferMoney product) {
        this.product = product;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
