package com.alodiga.app.wallet.utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

public class WebService {

    public static SoapObject invokeGetAutoConfigString(HashMap<String, String> map, String webMethName, String namespace) {
        SoapObject response = null;
        // Create request
        SoapObject request = null;

        if (namespace == Constants.ALODIGA)
            request = new SoapObject(Constants.CONSTANT_NAMESPACE_QA_ALODIGA, webMethName);
        else if (namespace == Constants.REGISTRO_UNIFICADO) {
            request = new SoapObject(Constants.CONSTANT_NAMESPACE_QA_REGISTRO_UNIFICADO, webMethName);
        }else if (namespace == Constants.REMITTANCE){
            request = new SoapObject(Constants.CONSTANT_NAMESPACE_REMITTANCE, webMethName);
        }

        // Property which holds input parameters
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                PropertyInfo propInfo = new PropertyInfo();
                propInfo.name = entry.getKey();
                propInfo.type = PropertyInfo.STRING_CLASS;
                request.addProperty(propInfo, entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        String url = Utils.getUrl(namespace).trim();
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        try {
            androidHttpTransport.call("", envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
