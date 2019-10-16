package com.alodiga.app.wallet.utils;

import com.alodiga.app.wallet.model.ObjUserHasProduct;

import java.util.ArrayList;

public  class Session {


    private static String userId = "";

    private static String username = "";
    private static String email = "";
    private static String phoneNumber = "";
    private static String country = "";
    private static String accountNumber = "";
    private static String alocoinsBalance = "";
    private static String alodigaBalance = "";
    private static String HealthCareCoinsBalance = "";

    private static String destinationAccountNumber = "";
    private static String destinationPhoneValue = "";
    private static String destinationLastNameValue = "";
    private static String DestinationNameValue = "";
    private static String destinationConcept = "";
    private static String getDestinationAmount = "";
    private static String usuarioDestionId = "";
    private static String mobileCodeSms;
    private static ObjUserHasProduct moneySelected;
    private static String codeOperation;
    private static ArrayList<ObjUserHasProduct> objUserHasProducts;

    public static ArrayList<ObjUserHasProduct> getObjUserHasProducts() {
        return objUserHasProducts;
    }

    public static void setObjUserHasProducts(ArrayList<ObjUserHasProduct> objUserHasProducts) {
        Session.objUserHasProducts = objUserHasProducts;
    }

    public static String getMobileCodeSms() {
        return mobileCodeSms;
    }

    public static void setMobileCodeSms(String mobileCodeSms) {
        Session.mobileCodeSms = mobileCodeSms;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Session.userId = userId;
    }

    public static String getDestinationConcept() {
        return destinationConcept;
    }

    public static void setDestinationConcept(String destinationConcept) {
        Session.destinationConcept = destinationConcept;
    }

    public static String getGetDestinationAmount() {
        return getDestinationAmount;
    }

    public static void setGetDestinationAmount(String getDestinationAmount) {
        Session.getDestinationAmount = getDestinationAmount;
    }

    public static ObjUserHasProduct getMoneySelected() {
        return moneySelected;
    }

    public static void setMoneySelected(ObjUserHasProduct moneySelected) {
        Session.moneySelected = moneySelected;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Session.email = email;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        Session.phoneNumber = phoneNumber;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        Session.country = country;
    }

    public static String getAccountNumber() {
        return accountNumber;
    }

    public static void setAccountNumber(String accountNumber) {
        Session.accountNumber = accountNumber;
    }

    public static String getAlocoinsBalance() {
        return alocoinsBalance;
    }

    public static void setAlocoinsBalance(String alocoinsBalance) {
        Session.alocoinsBalance = alocoinsBalance;
    }

    public static String getAlodigaBalance() {
        return alodigaBalance;
    }

    public static void setAlodigaBalance(String alodigaBalance) {
        Session.alodigaBalance = alodigaBalance;
    }

    public static String getHealthCareCoinsBalance() {
        return HealthCareCoinsBalance;
    }

    public static void setHealthCareCoinsBalance(String healthCareCoinsBalance) {
        HealthCareCoinsBalance = healthCareCoinsBalance;
    }

    public static String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public static void setDestinationAccountNumber(String destinationAccountNumber) {
        Session.destinationAccountNumber = destinationAccountNumber;
    }

    public static String getDestinationPhoneValue() {
        return destinationPhoneValue;
    }

    public static void setDestinationPhoneValue(String destinationPhoneValue) {
        Session.destinationPhoneValue = destinationPhoneValue;
    }

    public static String getDestinationLastNameValue() {
        return destinationLastNameValue;
    }

    public static void setDestinationLastNameValue(String destinationLastNameValue) {
        Session.destinationLastNameValue = destinationLastNameValue;
    }

    public static String getUsuarioDestionId() {
        return usuarioDestionId;
    }

    public static void setUsuarioDestionId(String usuarioDestionId) {
        Session.usuarioDestionId = usuarioDestionId;
    }

    public static String getDestinationNameValue() {
        return DestinationNameValue;
    }

    public static void setDestinationNameValue(String destinationNameValue) {
        DestinationNameValue = destinationNameValue;
    }

    public static String getCodeOperation() {
        return codeOperation;
    }

    public static void setCodeOperation(String codeOperation) {
        Session.codeOperation = codeOperation;
    }

    public static void clearALL(){
        Session.userId = null;
        Session.username = null;
        Session.email = null;
        Session.phoneNumber = null;
        Session.country = null;
        Session.accountNumber = null;
        Session.alocoinsBalance = null;
        Session.alodigaBalance = null;
        Session.HealthCareCoinsBalance = null;
        Session.destinationAccountNumber = null;
        Session.destinationPhoneValue = null;
        Session.destinationLastNameValue = null;
        Session.DestinationNameValue = null;
        Session.destinationConcept = null;
        Session.getDestinationAmount = null;
        Session.usuarioDestionId = null;
        Session.mobileCodeSms= null;
        Session.moneySelected = null;
        Session.codeOperation= null;
    }

}
