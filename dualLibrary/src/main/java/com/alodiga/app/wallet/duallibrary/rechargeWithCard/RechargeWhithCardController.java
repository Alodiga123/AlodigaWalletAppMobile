package com.alodiga.app.wallet.duallibrary.rechargeWithCard;

import com.alodiga.app.wallet.duallibrary.model.ObjCreditCardTypeId;
import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RechargeWhithCardController {

    public static SoapObject getStatusRecharge(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("userId", Session.getUserId());
        map.put("paymentInfoId", Session.getTarjetahabienteSelect().getCardInfo().getId());
        map.put("status", "false");

       return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_CHANGE_STATUS_PAYMENT_INFO, Constants.ALODIGA);

    }

    public static SoapObject getInfo(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("userId", Session.getUserId());


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETPAYMENTINFO, Constants.ALODIGA);
    }

    public static SoapObject saveInfo(String getCcv,ObjGenericObject getYear,ObjGenericObject getMonth){
        String responseCode;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("userId", Session.getUserId());
        map.put("estado", "");
        map.put("ciudad", "");
        map.put("zipCode", "");
        map.put("addres1", "");
        map.put("paymentPatnerId", "2");
        map.put("paymentTypeId", "1");
        map.put("creditCardTypeId", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getId());
        map.put("creditCardName", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
        map.put("creditCardNumber", Utils.aloDesencript(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber()));
        map.put("creditCardCVV", getCcv);
        map.put("creditCardDate", getYear.getName()+"-"+getMonth.getName());


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_SAVEPAYMENTINFO, Constants.ALODIGA);

    }

    public static SoapObject getCredit(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GET_CREDIT_CARD_TYPE, Constants.ALODIGA);

    }



    public static ObjCreditCardTypeId[] getListCardType(SoapObject response) {
        ObjCreditCardTypeId[] obj2 = new ObjCreditCardTypeId[response.getPropertyCount() - 3];

        int aux= 0;
        for (int i = 3; i < response.getPropertyCount(); i++) {

            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjCreditCardTypeId object = new ObjCreditCardTypeId(obj.getProperty("enabled").toString(), obj.getProperty("id").toString(),obj.getProperty("lengh").toString(), obj.getProperty("name").toString());
            obj2[aux] = object;
            aux++;
        }

        return obj2;
    }


    public static ObjTransferMoney[] getListProduct1(SoapObject response) {
        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];
        int aux= 0;
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
            obj2[aux] = object;
            aux++;
        }

        return obj2;
    }

    public static SoapObject getProductRecharge(){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userId", Session.getUserId());
            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_GETPRODUCTS_RECHARGE_PAYMENT_BY_USERID, Constants.ALODIGA);

        }


        public static SoapObject getCode(String clave){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            map.put("usuarioId", Session.getUserId());
            map.put("pin", clave);


            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);

        }

        public static SoapObject rechargeAfinitas(){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userId", Session.getUserId());
            map.put("amountRecharge", Session.getTarjetahabienteSelect().getAmount());
            map.put("currency", Constants.CURRENCY_INFO_PAYMENT);

            if (Session.getIsConstantsEmpty()){
                map.put("cardNumber", Utils.aloDesencript(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber()));
                map.put("expirationYear", Session.getTarjetahabienteSelect().getCardInfo().getYear());
                map.put("expirationMonth", Session.getTarjetahabienteSelect().getCardInfo().getMonth());
                map.put("cvv", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
                map.put("cardHolderName", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
            }else{
                map.put("paymentInfoId", Session.getTarjetahabienteSelect().getCardInfo().getId());
            }


           return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_SAVE_RECHARGE_AFINITAS, Constants.ALODIGA);

        }

        public static SoapObject saveStateInfo(){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userApi", Constants.WEB_SERVICES_USUARIOWS_);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            map.put("userId", Session.getUserId());
            map.put("estado", "");
            map.put("ciudad", "");
            map.put("zipCode", "");
            map.put("addres1", "");
            map.put("paymentPatnerId", "2");
            map.put("paymentTypeId", "1");
            map.put("creditCardTypeId", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardTypeId().getId());
            map.put("creditCardName", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardName());
            map.put("creditCardNumber", Utils.aloDesencript(Session.getTarjetahabienteSelect().getCardInfo().getCreditCardNumber()));
            map.put("creditCardCVV", Session.getTarjetahabienteSelect().getCardInfo().getCreditCardCVV());
            map.put("creditCardDate", Session.getTarjetahabienteSelect().getCardInfo().getYear()+"-"+Session.getTarjetahabienteSelect().getCardInfo().getMonth());


            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_SAVEPAYMENTINFO, Constants.ALODIGA);

        }

    public static ArrayList<ObjUserHasProduct> getListProduct(SoapObject response) {

        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 4; i < response.getPropertyCount()-1; i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(), obj.getProperty("name").toString(), obj.getProperty("currentBalance").toString(), obj.getProperty("symbol").toString(), obj.getProperty("isPayTopUp").toString());
            if (object.getName().equals("Tarjeta Prepagada") || object.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                object.setNumberCard(Session.getNumberCard_aux());
            }else{
                object.setNumberCard(Session.getAccountNumber_aux());
            }
            obj2.add(object);
        }

        return obj2;
    }

}
