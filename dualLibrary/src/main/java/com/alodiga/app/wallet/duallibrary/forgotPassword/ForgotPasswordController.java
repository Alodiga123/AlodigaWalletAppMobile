package com.alodiga.app.wallet.duallibrary.forgotPassword;

import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.Utils;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ForgotPasswordController {

    public static SoapObject getCodeSms(boolean isEmail, String data){

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            if (isEmail) {
                map.put("movil", " ");
                map.put("email", data);

            }else {
                map.put("movil", Utils.processPhone(data));
            }

            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL, Constants.REGISTRO_UNIFICADO);

        }

        public static SoapObject changePasswordForgot(String key){

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
                    map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
                    map.put("phoneOrEmail", Session.getForgotData());
                    map.put("credencial", key);

                    return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_CHANGE_PASSWORD_FORGOT, Constants.REGISTRO_UNIFICADO);

                }
}
