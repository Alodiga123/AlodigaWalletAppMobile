package com.alodiga.app.wallet.duallibrary.changePassword;

import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ChangePasswordController {

    public static SoapObject changePassword(String key){

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                map.put("usuarioId", Session.getUserId());
                map.put("credencial", key);

                return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_CHANGE_PASSWORD, Constants.REGISTRO_UNIFICADO);

            }
}
