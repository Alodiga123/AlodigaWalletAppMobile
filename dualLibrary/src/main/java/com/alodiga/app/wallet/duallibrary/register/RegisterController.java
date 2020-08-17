package com.alodiga.app.wallet.duallibrary.register;

import com.alodiga.app.wallet.duallibrary.model.ObjCountry;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class RegisterController {
    /**
     * @param phone
     * @return
     */
    public static String processPhone(String phone) {
        if (phone != null && phone.charAt(0) == '+')
            phone = phone.substring(1);
        return phone;
    }

    public static SoapObject sendCode(String phone){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("movil", processPhone(phone));


        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS, Constants.REGISTRO_UNIFICADO);

    }

    public static SoapObject getCountries(){
        HashMap<String, String> map = new HashMap<String, String>();

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_, Constants.ALODIGA);

    }


    public static ObjCountry[] getListCountry(SoapObject response) {

        ObjCountry[] obj2 = new ObjCountry[response.getPropertyCount() - 3];

        for (int i = 3; i < response.getPropertyCount(); i++) {
            SoapObject obj = (SoapObject) response.getProperty(i);
            String propiedad = response.getProperty(i).toString();
            ObjCountry object = new ObjCountry(obj.getProperty("code").toString(),obj.getProperty("name").toString());
            obj2[i - 3] = object;
        }

        return obj2;
    }

    public static SoapObject saveUser(String name, String lastName, String password, String email, String phoneNumber, String mobileCodeValidation, String pin){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("usuarioApi", Constants.WEB_SERVICES_USUARIOWS);
        map.put("passwordApi", Constants.WEB_SERVICES_PASSWORDWS);
        map.put("usuarioId", "");
        map.put("nombre", name);
        map.put("apellido", lastName);
        map.put("credencial", password);
        map.put("email", email);
        map.put("movil", phoneNumber.replace("+", ""));
        map.put("fechaNacimiento", "21-07-1988");
        map.put("direccion", "APP_MOBILE");
        map.put("paisId", "1");
        map.put("estadoId", "1");
        map.put("ciudadId", "1");
        map.put("codigoValidacionMovil", mobileCodeValidation);
        map.put("condadoId", "1");
        map.put("codigoPostal", "1006");
        map.put("nombreImagen", "AloCash App Android");
        map.put("imagenBytes", "null");
        map.put("link", "AloCash App Android");
        map.put("pin", pin);

        return WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_SAVE_USER, Constants.REGISTRO_UNIFICADO);

    }
}
