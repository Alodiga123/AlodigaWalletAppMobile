package com.alodiga.app.wallet.duallibrary.activeCard;

import com.alodiga.app.wallet.duallibrary.activateDesativateCard.ActivateDesativateCardController;
import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActiveCardController {

    public static SoapObject generateCode(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL, Constants.REGISTRO_UNIFICADO);
    }



    public static SoapObject activateCard(){


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("card", Utils.aloDesencript(Session.getCardActive().trim()));
        map.put("timeZone", ActivateDesativateCardController.getTimeZone().getID());
        map.put("status", Constants.ACTIVE_STATUS_ACTIVE);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_PROCESS, Constants.ALODIGA);
    }

    public static ArrayList<ObjUserHasProduct> getListProductGeneric(SoapObject response) {

        ArrayList<ObjUserHasProduct> obj2 = new ArrayList<>();
        for (int i = 4; i < response.getPropertyCount()-1; i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjUserHasProduct object = new ObjUserHasProduct(obj.getProperty("id").toString(), obj.getProperty("name").toString(), obj.getProperty("currentBalance").toString(), obj.getProperty("symbol").toString(), obj.getProperty("isPayTopUp").toString());
            if (object.getName().equals("Tarjeta Prepagada") || object.getName().equals("Prepaid Card") ){
                Session.setAffiliatedCard(Boolean.parseBoolean(Session.getPrepayCardAsociate()));
                object.setNumberCard(Session.getNumberCard());
                Session.setAffiliatedCard(true);
                Session.setPrepayCard("true");
            }else{
                object.setNumberCard(Session.getAccountNumber());
            }
            obj2.add(object);
        }

        return obj2;
    }

}
