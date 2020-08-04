package com.alodiga.app.wallet.duallibrary.manualRecharge;

import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.model.ObjTransferMoney;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class ManualRechargeController {

    public static SoapObject getCountries(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", Session.getUserId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES, Constants.ALODIGA);
    }

    public static SoapObject getBank(ObjGenericObject pais){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("countryId", pais.getId());
        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_BANK, Constants.ALODIGA);
    }

    public static SoapObject getProduct(ObjGenericObject bank){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("bankId", bank.getId());
        map.put("userId", Session.getUserId().trim());
        return  WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_PRODUCT, Constants.ALODIGA);
    }

    public static ObjGenericObject[] getListGeneric(SoapObject response) {

        ObjGenericObject[] obj2 = new ObjGenericObject[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjGenericObject object = new ObjGenericObject(obj.getProperty("name").toString(), obj.getProperty("id").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

    public static ObjTransferMoney[] getListProduct(SoapObject response) {

        ObjTransferMoney[] obj2 = new ObjTransferMoney[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjTransferMoney object = new ObjTransferMoney(obj.getProperty("id").toString(), obj.getProperty("name").toString() + " - " + obj.getProperty("currentBalance").toString(), obj.getProperty("currentBalance").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

    public static SoapObject rechargeManual(ObjGenericObject getbank, String getNumberOperation, String getAmountRecharge, ObjTransferMoney getproduct, String getTrans){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("bankId", getbank.getId());
        map.put("emailUser", Session.getEmail());
        map.put("referenceNumberOperation", getNumberOperation);
        map.put("amountRecharge", getAmountRecharge);
        map.put("productId", getproduct.getId());
        map.put("conceptTransaction", getTrans);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_RECHARGE, Constants.ALODIGA);

    }

}
