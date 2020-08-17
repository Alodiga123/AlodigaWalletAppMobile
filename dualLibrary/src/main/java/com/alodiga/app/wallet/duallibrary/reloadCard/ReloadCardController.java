package com.alodiga.app.wallet.duallibrary.reloadCard;

import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ReloadCardController {

    public static SoapObject getCode(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL, Constants.REGISTRO_UNIFICADO);

    }

    public static SoapObject validCode(String clave){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            map.put("usuarioId", Session.getUserId());
            map.put("pin", clave);


            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);

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
