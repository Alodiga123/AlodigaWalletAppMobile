package com.alodiga.app.wallet.duallibrary.paymentComerce;

import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentComerceController {

    public static SoapObject getUsuarioMovil(int caseFind, String phoneOrEmail){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            String methodName = "";
            switch (caseFind) {

                case 0:
                    map.put("email", phoneOrEmail);
                    methodName = "getUsuarioporemail";
                    break;

                case 1:
                    map.put("movil", phoneOrEmail);
                    methodName = "getUsuariopormovil";
                    break;

                default:

                    break;
            }
            return WebService.invokeGetAutoConfigString(map, methodName, Constants.REGISTRO_UNIFICADO);

        }


    public static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    public static SoapObject getUsuarioEmail(String phoneOrEmail){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            map.put("email", phoneOrEmail);
            String methodName = "getUsuarioporemail";

            return WebService.invokeGetAutoConfigString(map, methodName, Constants.REGISTRO_UNIFICADO);

        }

        public static SoapObject getCode(String clave){

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("usuarioId", Session.getUserId());
                map.put("pin", clave);


                return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);

            }


    public static ArrayList<ObjUserHasProduct> getListProduct(SoapObject response) {

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

    public static SoapObject paymentComerce(String  cryptogramShop, String emailUser, String  productId, String amountPayment, String conceptTransaction, String cryptogramUser, String idUserDestination ){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("cryptogramShop", cryptogramShop);
        map.put("emailUser", emailUser);
        map.put("productId", productId);
        map.put("amountPayment", amountPayment);
        map.put("conceptTransaction", conceptTransaction);
        map.put("cryptogramUser", cryptogramUser);
        map.put("idUserDestination", idUserDestination);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_PAYMENT_COMERCE, Constants.ALODIGA);

    }
}
