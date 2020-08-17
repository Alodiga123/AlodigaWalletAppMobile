package com.alodiga.app.wallet.duallibrary.balance;

import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class BalanceController {

    public static SoapObject activeDesactiveStatus(){

            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userId", Session.getUserId());
            map.put("card", Utils.aloDesencript(Session.getCardBalance().trim()));
            map.put("timeZone", tz.getID());

            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_ACTIVE_DEACTIVE_STATUS, Constants.ALODIGA);

        }

        public static SoapObject getBalance(){
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userId", Session.getUserId());
            map.put("card", Utils.aloDesencript(Session.getCardBalance().trim()));
            String card = Session.getCardBalance();
            map.put("timeZone", tz.getID());

            return  WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_CHECK_BALANCE, Constants.ALODIGA);
        }
}
