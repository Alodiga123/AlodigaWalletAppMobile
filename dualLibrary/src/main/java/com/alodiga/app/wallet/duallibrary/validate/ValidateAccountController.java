package com.alodiga.app.wallet.duallibrary.validate;

import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ValidateAccountController {

    public static SoapObject getKYC(String idioma){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("language",idioma);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_KYC_COLLECTION, Constants.ALODIGA);

    }

    public static SoapObject KYCProcess(String getedtstate_, String getedtcity_, String getedtcode_,String getedtAv_, String imagen, String imagen2){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        map.put("estado", getedtstate_);
        map.put("ciudad", getedtcity_);
        map.put("zipCode", getedtcode_);
        map.put("addres1", getedtAv_);
        map.put("imgDocument", imagen);
        map.put("imgProfile", imagen2);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_KYC_PROCESS, Constants.ALODIGA);

    }
}
