package com.alodiga.app.wallet.duallibrary.model;

import java.util.Date;

public class ObjPaymentInfo {

    private String creditCardCVV;
    private Date creditCardDate;
    private String month;
    private String year;
    private String creditCardName;
    private String creditCardNumber;
    private String creditCardNumberEnmas;
    private ObjCreditCardTypeId  creditCardTypeId;
    private Boolean isEnabled;
    private String id;
    private String paymentPatnerId;
    private String paymentTypeId;
    private String userId;
    private  int imageCard;

    public int getImageCard() {
        return imageCard;
    }

    public void setImageCard(int imageCard) {
        this.imageCard = imageCard;
    }

    public ObjPaymentInfo() {
    }

    public String getCreditCardNumberEnmas() {
        return creditCardNumberEnmas;
    }

    public void setCreditCardNumberEnmas(String creditCardNumberEnmas) {
        this.creditCardNumberEnmas = creditCardNumberEnmas;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardCVV() {
        return creditCardCVV;
    }

    public void setCreditCardCVV(String creditCardCVV) {
        this.creditCardCVV = creditCardCVV;
    }

    public Date getCreditCardDate() {
        return creditCardDate;
    }

    public void setCreditCardDate(Date creditCardDate) {
        this.creditCardDate = creditCardDate;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public ObjCreditCardTypeId getCreditCardTypeId() {
        return creditCardTypeId;
    }

    public void setCreditCardTypeId(ObjCreditCardTypeId creditCardTypeId) {
        this.creditCardTypeId = creditCardTypeId;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentPatnerId() {
        return paymentPatnerId;
    }

    public void setPaymentPatnerId(String paymentPatnerId) {
        this.paymentPatnerId = paymentPatnerId;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
