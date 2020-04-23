package com.alodiga.app.wallet.utils;

import android.graphics.Bitmap;

import com.alodiga.app.wallet.model.ObjDireccion;
import com.alodiga.app.wallet.model.ObjExchange;
import com.alodiga.app.wallet.model.ObjPaymentInfo;
import com.alodiga.app.wallet.model.ObjProccessRemittence;
import com.alodiga.app.wallet.model.ObjRemittencePay;
import com.alodiga.app.wallet.model.ObjRemittencePerson;
import com.alodiga.app.wallet.model.ObjResumeRemittence;
import com.alodiga.app.wallet.model.ObjTarjetahabiente;
import com.alodiga.app.wallet.model.ObjTopUpInfos;
import com.alodiga.app.wallet.model.ObjUserHasProduct;
import com.alodiga.app.wallet.model.ObjtopUpInfos_IsOpenRange;

import java.util.ArrayList;

public class Session {


    private static String userId = "";

    private static String username = "";
    private static String name = "";
    private static String lastname = "";
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
    private static boolean isactivateCard;
    private static String cardSelectActiveDeactive;
    private static String cardBalance;
    private static String cardBalanceMain;
    private static String prepayCard;
    private static String numberCard;
    private static String prepayCardAsociate;
    private static String tranferenceCardToCard;
    private static String tranferenceCardToCardEncrip;
    private static String tranferenceCardToCardDest;
    private static String tranferenceCardToCardEncripDest;
    private static String symbolCompanionCards;
    private static ObjRemittencePay pay;
    private static ObjDireccion direccionUsuario;
    private static ObjResumeRemittence ObjResumeRemittence;
    private static String remettencesDireccionId;
    private static ObjRemittencePerson remittenceDestinatario;
    private static ObjRemittencePerson remittenceRemitente;
    private static ObjProccessRemittence processRemittence;
    private static ObjTarjetahabiente TarjetahabienteSelect;
    private static ObjPaymentInfo paymentInfo;
    private static Boolean isConstantsEmpty;
    private static String  rechargeWhitCardIdTransaccion;

    public static String getRechargeWhitCardIdTransaccion() {
        return rechargeWhitCardIdTransaccion;
    }

    public static void setRechargeWhitCardIdTransaccion(String rechargeWhitCardIdTransaccion) {
        Session.rechargeWhitCardIdTransaccion = rechargeWhitCardIdTransaccion;
    }

    public static Boolean getIsConstantsEmpty() {
        return isConstantsEmpty;
    }

    public static void setIsConstantsEmpty(Boolean isConstantsEmpty) {
        Session.isConstantsEmpty = isConstantsEmpty;
    }

    public static ObjPaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public static void setPaymentInfo(ObjPaymentInfo paymentInfo) {
        Session.paymentInfo = paymentInfo;
    }


    public static ObjTarjetahabiente getTarjetahabienteSelect() {
        return TarjetahabienteSelect;
    }

    public static void setTarjetahabienteSelect(ObjTarjetahabiente tarjetahabienteSelect) {
        TarjetahabienteSelect = tarjetahabienteSelect;
    }

    public static ObjProccessRemittence getProcessRemittence() {
        return processRemittence;
    }

    public static void setProcessRemittence(ObjProccessRemittence processRemittence) {
        Session.processRemittence = processRemittence;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Session.name = name;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        Session.lastname = lastname;
    }

    public static ObjRemittencePerson getRemittenceDestinatario() {
        return remittenceDestinatario;
    }

    public static void setRemittenceDestinatario(ObjRemittencePerson remittenceDestinatario) {
        Session.remittenceDestinatario = remittenceDestinatario;
    }

    public static ObjRemittencePerson getRemittenceRemitente() {
        return remittenceRemitente;
    }

    public static void setRemittenceRemitente(ObjRemittencePerson remittenceRemitente) {
        Session.remittenceRemitente = remittenceRemitente;
    }

    public static String getRemettencesDireccionId() {
        return remettencesDireccionId;
    }

    public static void setRemettencesDireccionId(String remettencesDireccionId) {
        Session.remettencesDireccionId = remettencesDireccionId;
    }

    public static com.alodiga.app.wallet.model.ObjResumeRemittence getObjResumeRemittence() {
        return ObjResumeRemittence;
    }

    public static void setObjResumeRemittence(com.alodiga.app.wallet.model.ObjResumeRemittence objResumeRemittence) {
        ObjResumeRemittence = objResumeRemittence;
    }

    public static ObjDireccion getDireccionUsuario() {
        return direccionUsuario;
    }

    public static void setDireccionUsuario(ObjDireccion direccionUsuario) {
        Session.direccionUsuario = direccionUsuario;
    }

    public static ObjRemittencePay getPay() {
        return pay;
    }

    public static void setPay(ObjRemittencePay pay) {
        Session.pay = pay;
    }

    public static String getSymbolCompanionCards() {
        return symbolCompanionCards;
    }

    public static void setSymbolCompanionCards(String symbolCompanionCards) {
        Session.symbolCompanionCards = symbolCompanionCards;
    }

    public static String getTranferenceCardToCardDest() {
        return tranferenceCardToCardDest;
    }

    public static void setTranferenceCardToCardDest(String tranferenceCardToCardDest) {
        Session.tranferenceCardToCardDest = tranferenceCardToCardDest;
    }

    public static String getTranferenceCardToCardEncripDest() {
        return tranferenceCardToCardEncripDest;
    }

    public static void setTranferenceCardToCardEncripDest(String tranferenceCardToCardEncripDest) {
        Session.tranferenceCardToCardEncripDest = tranferenceCardToCardEncripDest;
    }

    public static String getTranferenceCardToCardEncrip() {
        return tranferenceCardToCardEncrip;
    }

    public static void setTranferenceCardToCardEncrip(String tranferenceCardToCardEncrip) {
        Session.tranferenceCardToCardEncrip = tranferenceCardToCardEncrip;
    }

    public static String getTranferenceCardToCard() {
        return tranferenceCardToCard;
    }

    public static void setTranferenceCardToCard(String tranferenceCardToCard) {
        Session.tranferenceCardToCard = tranferenceCardToCard;
    }

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

    public static boolean isIsactivateCard() {
        return isactivateCard;
    }

    public static void setIsactivateCard(boolean isactivateCard) {
        Session.isactivateCard = isactivateCard;
    }

    public static String getCardBalance() {
        return cardBalance;
    }

    public static void setCardBalance(String cardBalance) {
        Session.cardBalance = cardBalance;
    }

    public static String getCardBalanceMain() {
        return cardBalanceMain;
    }

    public static void setCardBalanceMain(String cardBalanceMain) {
        Session.cardBalanceMain = cardBalanceMain;
    }

    public static String getPrepayCard() {
        return prepayCard;
    }

    public static void setPrepayCard(String prepayCard) {
        Session.prepayCard = prepayCard;
    }

    public static String getNumberCard() {
        return numberCard;
    }

    public static void setNumberCard(String numberCard) {
        Session.numberCard = numberCard;
    }

    public static String getPrepayCardAsociate() {
        return prepayCardAsociate;
    }

    public static void setPrepayCardAsociate(String prepayCardAsociate) {
        Session.prepayCardAsociate = prepayCardAsociate;
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
        Session.cardBalance=null;
        Session.cardBalanceMain= null;
        Session.prepayCard= null;
        Session.prepayCardAsociate= null;
        Session.numberCard= null;
        Session.tranferenceCardToCard= null;
        Session.tranferenceCardToCardEncrip= null;
        Session.tranferenceCardToCardDest= null;
        Session.tranferenceCardToCardEncripDest= null;
    }

}
