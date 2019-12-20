package com.alodiga.app.wallet.utils;

import android.graphics.Bitmap;
import android.se.omapi.SEService;

import com.alodiga.app.wallet.model.ObjExchange;
import com.alodiga.app.wallet.model.ObjTopUpInfos;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.model.ObjtopUpInfos_IsOpenRange;

import java.util.ArrayList;

public class Session {


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
    private static String numberDestinationTopup;
    private static String phonenumberTopup;
    private static String languajeTopup;
    private static String countryTopup;
    private static String typeTopup;
    private static String operatorTopup;
    private static ObjtopUpInfos_IsOpenRange objIsOpenRangeTopup;
    private static ObjTopUpInfos[] datosDenominacionFijaTopup;
    private static String DestinationAmountTopup;
    private static String operationTopup;
    private static String operationPaymentComerce;
    private static String operationTransference;
    private static ObjExchange exchange;
    private static String operationExchange;
    private static String forgotData;
    private static Bitmap selectedImage;
    private static Bitmap selectedImageSelfie;
    private static String cumplimient;
    private static String cardActive;
    private static String cardDeactive;
    private static boolean affiliatedCard;
    private static String cardSelectActiveDeactive;





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

    public static String getNumberDestinationTopup() {
        return numberDestinationTopup;
    }

    public static void setNumberDestinationTopup(String numberDestinationTopup) {
        Session.numberDestinationTopup = numberDestinationTopup;
    }

    public static String getPhonenumberTopup() {
        return phonenumberTopup;
    }

    public static void setPhonenumberTopup(String phonenumberTopup) {
        Session.phonenumberTopup = phonenumberTopup;
    }

    public static String getLanguajeTopup() {
        return languajeTopup;
    }

    public static void setLanguajeTopup(String languajeTopup) {
        Session.languajeTopup = languajeTopup;
    }

    public static String getCountryTopup() {
        return countryTopup;
    }

    public static void setCountryTopup(String countryTopup) {
        Session.countryTopup = countryTopup;
    }

    public static String getTypeTopup() {
        return typeTopup;
    }

    public static void setTypeTopup(String typeTopup) {
        Session.typeTopup = typeTopup;
    }

    public static String getOperatorTopup() {
        return operatorTopup;
    }

    public static void setOperatorTopup(String operatorTopup) {
        Session.operatorTopup = operatorTopup;
    }

    public static ObjtopUpInfos_IsOpenRange getObjIsOpenRangeTopup() {
        return objIsOpenRangeTopup;
    }

    public static void setObjIsOpenRangeTopup(ObjtopUpInfos_IsOpenRange objIsOpenRangeTopup) {
        Session.objIsOpenRangeTopup = objIsOpenRangeTopup;
    }

    public static ObjTopUpInfos[] getDatosDenominacionFijaTopup() {
        return datosDenominacionFijaTopup;
    }

    public static void setDatosDenominacionFijaTopup(ObjTopUpInfos[] datosDenominacionFijaTopup) {
        Session.datosDenominacionFijaTopup = datosDenominacionFijaTopup;
    }

    public static String getDestinationAmountTopup() {
        return DestinationAmountTopup;
    }

    public static void setDestinationAmountTopup(String destinationAmountTopup) {
        DestinationAmountTopup = destinationAmountTopup;
    }

    public static String getOperationTopup() {
        return operationTopup;
    }

    public static void setOperationTopup(String operationTopup) {
        Session.operationTopup = operationTopup;
    }

    public static ObjExchange getExchange() {
        return exchange;
    }

    public static void setExchange(ObjExchange exchange) {
        Session.exchange = exchange;
    }

    public static String getOperationExchange() {
        return operationExchange;
    }

    public static void setOperationExchange(String operationExchange) {
        Session.operationExchange = operationExchange;
    }

    public static String getForgotData() {
        return forgotData;
    }

    public static void setForgotData(String forgotData) {
        Session.forgotData = forgotData;
    }

    public static String getOperationPaymentComerce() {
        return operationPaymentComerce;
    }

    public static void setOperationPaymentComerce(String operationPaymentComerce) {
        Session.operationPaymentComerce = operationPaymentComerce;
    }

    public static String getOperationTransference() {
        return operationTransference;
    }

    public static void setOperationTransference(String operationTransference) {
        Session.operationTransference = operationTransference;
    }

    public static Bitmap getSelectedImage() {
        return selectedImage;
    }

    public static void setSelectedImage(Bitmap selectedImage) {
        Session.selectedImage = selectedImage;
    }

    public static Bitmap getSelectedImageSelfie() {
        return selectedImageSelfie;
    }

    public static void setSelectedImageSelfie(Bitmap selectedImageSelfie) {
        Session.selectedImageSelfie = selectedImageSelfie;
    }

    public static String getCumplimient() {
        return cumplimient;
    }

    public static void setCumplimient(String cumplimient) {
        Session.cumplimient = cumplimient;
    }

    public static String getCardActive() {
        return cardActive;
    }

    public static void setCardActive(String cardActive) {
        Session.cardActive = cardActive;
    }

    public static String getCardDeactive() {
        return cardDeactive;
    }

    public static void setCardDeactive(String cardDeactive) {
        Session.cardDeactive = cardDeactive;
    }

    public static boolean isAffiliatedCard() {
        return affiliatedCard;
    }

    public static void setAffiliatedCard(boolean affiliatedCard) {
        Session.affiliatedCard = affiliatedCard;
    }

    public static String getCardSelectActiveDeactive() {
        return cardSelectActiveDeactive;
    }

    public static void setCardSelectActiveDeactive(String cardSelectActiveDeactive) {
        Session.cardSelectActiveDeactive = cardSelectActiveDeactive;
    }

    public static void clearALL() {
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
        Session.mobileCodeSms = null;
        Session.moneySelected = null;
        Session.codeOperation = null;
        Session.numberDestinationTopup= null;
        Session.phonenumberTopup= null;
        Session.languajeTopup= null;
        Session.countryTopup= null;
        Session.typeTopup= null;
        Session.objUserHasProducts.clear();
        Session.operatorTopup=null;
        Session.objIsOpenRangeTopup = null;
        Session.datosDenominacionFijaTopup= null;
        Session.DestinationAmountTopup= null;
        Session.operationTopup=null;
        Session.exchange= null;
        Session.operationExchange = null;
        Session.operationPaymentComerce=null;
        Session.operationTransference=null;
        Session.selectedImage= null;
        Session.selectedImageSelfie= null;
        Session.cumplimient= null;
        Session.cardActive= null;
        Session.cardDeactive= null;
        Session.cardSelectActiveDeactive= null;
    }

}
