package com.alodiga.app.wallet.duallibrary.utils;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class CommonController {

    public  static SoapObject getCode(String clave){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("usuarioId", Session.getUserId());
        map.put("pin", clave);
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_VALID_CODE, Constants.REGISTRO_UNIFICADO);
    }

    public static StringBuilder setDecimal(CharSequence s){

        String userInput = "" + s.toString().replaceAll("[^\\d]", "");
        StringBuilder cashAmountBuilder = new StringBuilder(userInput);

        while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
            cashAmountBuilder.deleteCharAt(0);
        }
        while (cashAmountBuilder.length() < 3) {
            cashAmountBuilder.insert(0, '0');
        }
        cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
        cashAmountBuilder.insert(0, ' ');

        return cashAmountBuilder;
    }
}
