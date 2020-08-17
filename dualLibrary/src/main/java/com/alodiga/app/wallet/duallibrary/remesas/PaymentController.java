package com.alodiga.app.wallet.duallibrary.remesas;

import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjRemittencePay;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class PaymentController {

    public static SoapObject getProductRemmettence(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETPRODUCTSREMETTENCEBYUSER, Constants.ALODIGA);
    }

    public static ObjTransferMoney[] getListProduct(SoapObject response) {
        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

    public static ObjGenericObject[] getListGeneric_contry(SoapObject response) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 2];

        for (int i = 2; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("id").toString());
            obj2[i - 2] = object;
        }

        return obj2;
    }

    public static SoapObject getCountry(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login", Constants.USUARIO);
        map.put("password", Constants.PASSWORD);
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN,Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getContryToken(String token){
        HashMap<String, String> map_ = new HashMap<String, String>();
        map_.put("token", token);

        return WebService.invokeGetAutoConfigString(map_, Constants.WEB_SERVICES_METHOD_COUNTRIES, Constants.REMITTANCE,Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getCorrespondent(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login", Constants.USUARIO);
        map.put("password", Constants.PASSWORD);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE,Constants.CONSTANT_WSREMITTENCEMOBILE);

    }
    public static SoapObject gettoken(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login", Constants.USUARIO);
        map.put("password", Constants.PASSWORD);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE,Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getDelivery(String token, ObjGenericObject correspondent){
        HashMap<String, String> map_c = new HashMap<String, String>();
        map_c.put("token", token);
        map_c.put("paymentNetworkId", correspondent.getId());
        return WebService.invokeGetAutoConfigString(map_c, Constants.WEB_SERVICES_METHOD_LOADDELIVERYFORMBYPAYMENTNETWORK, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getTokenLo(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login", Constants.USUARIO);
        map.put("password", Constants.PASSWORD);

       return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_LOGIN, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }


    public static SoapObject getRemmittenceResume(String token, ObjRemittencePay pay ){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("countrySourceId", Session.getDireccionUsuario().getPaisId());
        map.put("countryId", pay.getDestination_country().getId());
        map.put("ratePaymentNetworkId", pay.getDelivery_method().getId());
        map.put("realAmountToSend", pay.getAmount_());
        map.put("isIncludeRate", String.valueOf(pay.getRate_included()));


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_REMETTENCE_SUMARY, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getPais(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getState(ObjGenericObject pais){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("countryCode", pais.getId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETSTATESBYCOUNTRY, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getCiudad(ObjGenericObject estado){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("stateCode", estado.getId());
        return  WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETCITIESBYSTATE, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }


    public static ObjGenericObject[] getListGeneric(SoapObject response) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 2];

        for (int i = 2; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("id").toString());

            obj2[i - 2] = object;
        }

        return obj2;
    }



    public static SoapObject getState(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("countryCode", Session.getPay().getDestination_country().getId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETSTATESBYCOUNTRY, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject getCiuddad1(ObjGenericObject estado){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("stateCode", estado.getId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETCITIESBYSTATE, Constants.REMITTANCE, Constants.CONSTANT_WSREMITTENCEMOBILE);

    }

    public static SoapObject proccesRemittence(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("amountOrigin", Session.getObjResumeRemittence().getRealAmountToSend());
        map.put("totalAmount", Session.getObjResumeRemittence().getAmountToSendRemettence());
        map.put("amountDestiny", Session.getObjResumeRemittence().getReceiverAmount());
        //map.put("correspondentId", Session.getPay().getCorrespondent().getId());
        map.put("exchangeRateId",Session.getObjResumeRemittence().getExchangeRateSource().getId());
        map.put("ratePaymentNetworkId", Session.getObjResumeRemittence().getRatePaymentNetwork().getId());
        map.put("originCurrentId", Session.getObjResumeRemittence().getExchangeRateSource().getCurrency().getId());
        map.put("destinyCurrentId", Session.getObjResumeRemittence().getExchangeRateDestiny().getCurrency().getId());
        map.put("paymentNetworkId", Session.getObjResumeRemittence().getRatePaymentNetwork().getPaymentNetwork().getId());
        map.put("deliveryFormId", Session.getPay().getDelivery_method().getId());


        map.put("addressId", Session.getRemettencesDireccionId());

        if(Session.getRemettencesDireccionId().equals(Constants.REMITTENCE_ID)){
            map.put("remittentCountryId", Session.getRemittenceRemitente().getLocation().getId());
            map.put("remittentStateId", Session.getRemittenceRemitente().getState().getId());
            map.put("remittentStateName", Session.getRemittenceRemitente().getState().getName());
            map.put("remittentCityName", Session.getRemittenceRemitente().getCity().getName());
            map.put("remittentCityId", Session.getRemittenceRemitente().getCity().getId());
            map.put("remittentAddress", Session.getRemittenceRemitente().getAv());
            map.put("remittentZipCode", Session.getRemittenceRemitente().getCodeZip());
        }else{
            map.put("remittentCountryId", "");
            map.put("remittentStateId",  "");
            map.put("remittentStateName", "");
            map.put("remittentCityName", "");
            map.put("remittentCityId",  "");
            map.put("remittentAddress",  "");
            map.put("remittentZipCode",  "");
        }


        map.put("receiverFirstName", Session.getRemittenceDestinatario().getName());
        map.put("receiverMiddleName", Session.getRemittenceDestinatario().getSecondname());
        map.put("receiverLastName", Session.getRemittenceDestinatario().getLastName());
        map.put("receiverSecondSurname", Session.getRemittenceDestinatario().getSecondSurmane());
        map.put("receiverPhoneNumber", Session.getRemittenceDestinatario().getTelephone());
        map.put("receiverEmail", Session.getRemittenceDestinatario().getEmail());
        map.put("receiverCountryId", Session.getRemittenceDestinatario().getLocation().getId());
        map.put("receiverCityId", Session.getRemittenceDestinatario().getCity().getId());
        map.put("receiverCityName", Session.getRemittenceDestinatario().getCity().getName());
        map.put("receiverStateId", Session.getRemittenceDestinatario().getState().getId());
        map.put("receiverStateName", Session.getRemittenceDestinatario().getState().getName());
        map.put("receiverAddress", Session.getRemittenceDestinatario().getAv());
        map.put("receiverZipCode", Session.getRemittenceDestinatario().getCodeZip());


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_PROCESSREMETTENCEACCOUNT, Constants.ALODIGA);

    }


}
