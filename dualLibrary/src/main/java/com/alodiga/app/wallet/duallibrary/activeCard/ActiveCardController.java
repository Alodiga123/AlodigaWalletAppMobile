package com.alodiga.app.wallet.duallibrary.activeCard;

import com.alodiga.app.wallet.duallibrary.activateDesativateCard.ActivateDesativateCardController;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ActiveCardController {

    public static SoapObject generateCode(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL, Constants.REGISTRO_UNIFICADO);
    }


    public  static SoapObject getCode(String clave){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("usuarioId", Session.getUserId());
        map.put("pin", clave);
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);
    }

    public static SoapObject activateCard(){


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("card", Utils.aloDesencript(Session.getCardActive().trim()));
        map.put("timeZone", ActivateDesativateCardController.getTimeZone().getID());
        map.put("status", Constants.ACTIVE_STATUS_ACTIVE);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_PROCESS, Constants.ALODIGA);
    }

}
