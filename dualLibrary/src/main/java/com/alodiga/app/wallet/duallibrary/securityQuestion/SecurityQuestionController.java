package com.alodiga.app.wallet.duallibrary.securityQuestion;

import com.alodiga.app.wallet.duallibrary.model.ObjGenericObject;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecurityQuestionController {

    public static int getLenghtFromResponseJson(String v) {
        return (v.split("pregunta=").length);
    }


    public static List<ObjGenericObject> getParseResponse(String response) {
        List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();
        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {
            String name = response.split("pregunta=")[i].split(";")[0];
            String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];
            ObjGenericObject objGenericObject = new ObjGenericObject(name, idValue);
            objGenericObjectList.add(objGenericObject);
        }
        return objGenericObjectList;
    }


    public static List<ObjGenericObject> getParseResponse(String response, String id) {
        List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();

        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

            String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];

            if (idValue.equals(id)) {
                continue;
            }

            String name = response.split("pregunta=")[i].split(";")[0];
            ObjGenericObject objGenericObject = new ObjGenericObject(name, idValue);
            objGenericObjectList.add(objGenericObject);
        }
        return objGenericObjectList;
    }

    public static List<ObjGenericObject> getParseResponse(String response, String id, String id1) {
        List<ObjGenericObject> objGenericObjectList = new ArrayList<ObjGenericObject>();

        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

            String idValue = (response.split("pregunta=")[i]).split("=")[1].split(";")[0];

            if (idValue.equals(id) || idValue.equals(id1)) {
                continue;
            }

            String name = response.split("pregunta=")[i].split(";")[0];
            ObjGenericObject objGenericObject = new ObjGenericObject(name, idValue);
            objGenericObjectList.add(objGenericObject);
        }
        return objGenericObjectList;
    }


    public static SoapObject getSecurityQuestion(){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
            map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
            map.put("IdIdioma", "4");

            return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SECRET_AMSWER, Constants.REGISTRO_UNIFICADO);
       }


       public static  SoapObject sendSecurityQuestion( ObjGenericObject object_select, String getedtAnswer1, ObjGenericObject object2_select, String getedtAnswer2,ObjGenericObject  object3_select, String getedtAnswer3){

           HashMap<String, String> map = new HashMap<String, String>();
           map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
           map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
           map.put("usuarioId", Session.getUserId());
           map.put("preguntaId1", object_select.getId());
           map.put("repuestaId1", getedtAnswer1);
           map.put("preguntaId2", object2_select.getId());
           map.put("repuestaId2", getedtAnswer2);
           map.put("preguntaId3", object3_select.getId());
           map.put("repuestaId3", getedtAnswer3);

          return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_SECRET_AMSWER, Constants.REGISTRO_UNIFICADO);
       }
}
