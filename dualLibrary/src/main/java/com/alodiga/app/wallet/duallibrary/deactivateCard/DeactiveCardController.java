package com.alodiga.app.wallet.duallibrary.deactivateCard;

import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class DeactiveCardController {

    public static SoapObject smsSend(){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL, Constants.REGISTRO_UNIFICADO);
        }

        public static  SoapObject getCode(String clave){

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("usuarioId", Session.getUserId());
                map.put("pin", clave);

                return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);

        }

        public static SoapObject deactiveCard(){

                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", Session.getUserId());
                map.put("card", Utils.aloDesencript(Session.getCardDeactive().trim()));
                map.put("timeZone", tz.getID());
                map.put("status", Constants.ACTIVE_STATUS_DEACTIVE);


                return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_DEACTIVE_PROCESS, Constants.ALODIGA);

        }
}
