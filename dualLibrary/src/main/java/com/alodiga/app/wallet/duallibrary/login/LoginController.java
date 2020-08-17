package com.alodiga.app.wallet.duallibrary.login;

import com.alodiga.app.wallet.duallibrary.model.ObjUserHasProduct;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginController {

    public static String getValueFromResponseJson(String v, String response) {
        return (response.split(v + "=")[1].split(";")[0]);
    }

    public static Integer getLenghtFromResponseJson(String v, String response) {
        return (response.split(v).length);
    }

    public static SoapObject login(String mEmail, String mPassword){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("email", mEmail);
        map.put("credencial", mPassword);
        map.put("ip", "192.168.3.20");
        return  WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_LOGIN_APP_MOBILE, Constants.REGISTRO_UNIFICADO);
    }

    public static void setElementInitialSession(String nameSession, String phoneNumberSession, String emailSession, String alodigaBalanceSession, String accountNumberSession, String alocoinsBalanceSesssion, String userId, String healthCareCoinsBalanceSession, ArrayList<ObjUserHasProduct> objUserHasProducts, String cumplimient, String prepayCard, String prepayCardAsociate, String numberCard) {

        Session.setObjUserHasProducts(objUserHasProducts);
        Session.setUsername(nameSession);
        Session.setPhoneNumber(phoneNumberSession);
        Session.setEmail(emailSession);
        Session.setAlodigaBalance(alodigaBalanceSession);
        Session.setAccountNumber(accountNumberSession);
        Session.setAlocoinsBalance(alocoinsBalanceSesssion);
        Session.setUserId(userId);
        Session.setHealthCareCoinsBalance(healthCareCoinsBalanceSession);
        Session.setCumplimient(cumplimient);
        Session.setPrepayCard(prepayCard);
        Session.setPrepayCardAsociate(prepayCardAsociate);
        Session.setNumberCard(numberCard);
        Session.setNumberCard_aux(numberCard);

}
}
