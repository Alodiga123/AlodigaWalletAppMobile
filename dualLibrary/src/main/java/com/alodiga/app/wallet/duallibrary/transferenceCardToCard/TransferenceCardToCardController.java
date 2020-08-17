package com.alodiga.app.wallet.duallibrary.transferenceCardToCard;

import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TransferenceCardToCardController {

    public static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }


    public static SoapObject getUsuario(Integer caseFind, String phoneOrEmail ){

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


    public static SoapObject getUsuarioEmail( String phoneOrEmail ){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            map.put("email", phoneOrEmail);
            String methodName = "getUsuarioporemail";

            return  WebService.invokeGetAutoConfigString(map, methodName, Constants.REGISTRO_UNIFICADO);
        }

        public static SoapObject transferenceGetCard(){

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUsuarioDestionId());

                return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_TRANSFERENCE_GET_CARD, Constants.ALODIGA);

            }

            public static SoapObject validCode(String clave){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                    map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                    map.put("usuarioId", Session.getUserId());
                    map.put("pin", clave);

                    return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);

                }

    public static ArrayList<ObjUserHasProduct> getListProduct(SoapObject response) {

        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 5; i < response.getPropertyCount(); i++) {
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

    public static SoapObject transferenceCardToCard(String idUserDestination, String amountPayment, String conceptTransaction ){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("idUserDestination", idUserDestination);
                map.put("numberCardOrigin", Utils.aloDesencript(Session.getTranferenceCardToCard()));
                map.put("numberCardDestinate",Utils.aloDesencript(Session.getTranferenceCardToCardDest()));
                map.put("balance", amountPayment);
                map.put("conceptTransaction", conceptTransaction);

                return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_TRANSFERENCE_CARD_TO_CARD, Constants.ALODIGA);

            }

            public static SoapObject updtaeProduct(){

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                    map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                    map.put("usuarioId", Session.getUserId());


                    return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_UPDATE_PRODUCT, Constants.REGISTRO_UNIFICADO);
                   }
}
