package com.alodiga.app.wallet.duallibrary.exchange;

import com.alodiga.app.wallet.duallibrary.model.ObjExchange;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ExchangeController {


    public static SoapObject getProductExchange(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT_EXCHANGE, Constants.ALODIGA);
    }




    public static ObjTransferMoney[] getListProduct(SoapObject response) {

        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString(),obj.getProperty("symbol").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }


    public static SoapObject processPreviewExchange(ObjExchange exchange_aux, String amountExchange){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emailUser", Session.getEmail());
        map.put("productSourceId", exchange_aux.getExange_productSource().getId());
        map.put("productDestinationId",exchange_aux.getExange_productDestination().getId());
        map.put("amountExchange", amountExchange);
        map.put("includedAmount", exchange_aux.getExange_includedAmount());

        return  WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PREVIEW_EXCHANGE, Constants.ALODIGA);
    }

    public static SoapObject getExchange(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emailUser", Session.getEmail());
        map.put("productSourceId", Session.getExchange().getExange_productSource().getId());
        map.put("productDestinationId", Session.getExchange().getExange_productDestination().getId());
        map.put("amountExchange", Session.getExchange().getAmountExchange());
        map.put("conceptTransaction", "cualquier cosa");
        map.put("includedAmount", Session.getExchange().getExange_includedAmount());

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_EXCHANGE, Constants.ALODIGA);
    }

    public static ArrayList<ObjUserHasProduct> getListProductGeneric(SoapObject response) {

        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 4; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(), obj.getProperty("name").toString(), obj.getProperty("currentBalance").toString(), obj.getProperty("symbol").toString(), obj.getProperty("isPayTopUp").toString());
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
}
