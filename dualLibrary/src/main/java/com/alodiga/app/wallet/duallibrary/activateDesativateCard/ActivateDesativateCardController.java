package com.alodiga.app.wallet.duallibrary.activateDesativateCard;

import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class ActivateDesativateCardController {


     public static SoapObject getStatusCard() {

         HashMap<String, String> map = new HashMap<String, String>();
         map.put("userId", Session.getUserId());
         map.put("card", Utils.aloDesencript(Session.getCardSelectActiveDeactive().trim()));
         map.put("timeZone", getTimeZone().getID());

         return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_DEACTIVE_STATUS, Constants.ALODIGA);
     }


    public static SoapObject activarCard(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("card", Utils.aloDesencript(Session.getCardSelectActiveDeactive().trim()));
        map.put("timeZone", getTimeZone().getID());
        map.put("status", Constants.ACTIVE_STATUS_ACTIVE);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_PROCESS, Constants.ALODIGA);
    }

    public static SoapObject desactiveCard(){


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("card",Session.getCardSelectActiveDeactive());
        //Utils.aloEncrpter(Session.getCardSelectActiveDeactive());
        map.put("timeZone", getTimeZone().getID());
        map.put("status", Constants.ACTIVE_STATUS_DEACTIVE);

         return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_DEACTIVE_PROCESS, Constants.ALODIGA);
    }

    public static TimeZone getTimeZone(){

        Calendar cal = Calendar.getInstance();
        return cal.getTimeZone();
    }

    }
