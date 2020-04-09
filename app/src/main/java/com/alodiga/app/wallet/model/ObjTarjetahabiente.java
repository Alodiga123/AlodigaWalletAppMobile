package com.alodiga.app.wallet.model;

public class ObjTarjetahabiente {

    private String card_number;
    private String security_code;
    private String cardholder_name;
    private String type_card;
    private String expiration_date_moth;
    private String expiration_date_year;
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

    public ObjTarjetahabiente(String card_number, String security_code, String cardholder_name, String type_card, String expiration_date_moth, String expiration_date_year, String country, String state, String county, String city, String direction, String zip_code, ObjTransferMoney product, String amount) {
        this.card_number = card_number;
        this.security_code = security_code;
        this.cardholder_name = cardholder_name;
        this.type_card = type_card;
        this.expiration_date_moth = expiration_date_moth;
        this.expiration_date_year = expiration_date_year;
        this.country = country;
        this.state = state;
        this.county = county;
        this.city = city;
        this.direction = direction;
        this.zip_code = zip_code;
        this.product = product;
        this.amount = amount;
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

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getType_card() {
        return type_card;
    }

    public void setType_card(String type_card) {
        this.type_card = type_card;
    }

    public String getExpiration_date_moth() {
        return expiration_date_moth;
    }

    public void setExpiration_date_moth(String expiration_date_moth) {
        this.expiration_date_moth = expiration_date_moth;
    }

    public String getExpiration_date_year() {
        return expiration_date_year;
    }

    public void setExpiration_date_year(String expiration_date_year) {
        this.expiration_date_year = expiration_date_year;
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
