package com.alodiga.app.wallet.duallibrary.topup;

import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjTopUpInfos;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TopupController {

    public static SoapObject getCountry(){
        HashMap<String, String> map = new HashMap<String, String>();
         return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_TOPUP, Constants.ALODIGA);
    }


    public static SoapObject getLanguaje(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_LANGUAJE_TOPUP, Constants.ALODIGA);

    }


    public static ObjGenericObject[] getListGeneric(SoapObject response, boolean isContry) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 3];
        ObjGenericObject object;
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            if (isContry) {
                object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("code").toString());
            } else {
                object = new ObjGenericObject(obj.getProperty("description").toString(), obj.getProperty("id").toString());
            }
            obj2[i - 3] = object;
        }

        return obj2;
    }

    public static ObjTopUpInfos[] getDatosDenominacionFija(SoapObject response) {
        ObjTopUpInfos[] obj2 = new ObjTopUpInfos[response.getPropertyCount() - 3];
        ObjTopUpInfos object;
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);

            object = new ObjTopUpInfos(
                    obj.getProperty("commissionPercent").toString(),
                    obj.getProperty("country").toString(),
                    obj.getProperty("coutryId").toString(),
                    obj.getProperty("denomination").toString(),
                    obj.getProperty("denominationReceiver").toString(),
                    obj.getProperty("denominationSale").toString(),
                    obj.getProperty("destinationCurrency").toString(),
                    obj.getProperty("isOpenRange").toString(),
                    obj.getProperty("operatorid").toString(),
                    obj.getProperty("opertador").toString(),
                    obj.getProperty("skuid").toString(),
                    obj.getProperty("wholesalePrice").toString());

            obj2[i - 3] = object;
        }

        return obj2;

    }

    public static SoapObject getListTopup(String Phone, String receiverNumber){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("phoneNumber", Utils.processPhone(Phone));
        map.put("receiverNumber", Utils.processPhone(receiverNumber));

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_LIST_TOPUP, Constants.ALODIGA);

    }

    public static SoapObject getProductDeb(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT_TOPUP, Constants.ALODIGA);

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



    public static ArrayList<ObjUserHasProduct> getListProductGeneric(SoapObject response) {
        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(), obj.getProperty("name").toString(), obj.getProperty("currentBalance").toString(), obj.getProperty("symbol").toString(),obj.getProperty("isPayTopUp").toString());
            if (object.getName().equals("Tarjeta Prepagada") || object.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                object.setNumberCard(Session.getNumberCard());
            }else{
                object.setNumberCard(Session.getAccountNumber());
            }

            obj2.add(object);
        }

        return obj2;
    }


    public static SoapObject saveTopup(boolean isOR, String productid,ObjTopUpInfos objTopUpInfosS){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emailUser", Session.getEmail());
        map.put("productId", productid);
        map.put("cryptogramUser", "1");
        map.put("destinationNumber", Utils.processPhone(Session.getNumberDestinationTopup()));
        map.put("senderNumber", Utils.processPhone(Session.getPhonenumberTopup()));

        if (isOR) {
            map.put("skudId", Session.getObjIsOpenRangeTopup().getSkuid());
            map.put("amountRecharge", Session.getDestinationAmountTopup());
            map.put("amountPayment", Session.getDestinationAmountTopup());
        } else {
            map.put("skudId", objTopUpInfosS.getSkuid());
            map.put("amountRecharge", objTopUpInfosS.getDenomination());
            map.put("amountPayment", objTopUpInfosS.getDenomination());
        }

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SAVE_TOPUP, Constants.ALODIGA);

    }

}


