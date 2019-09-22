package com.example.c2paplicationmobile;

import android.provider.SyncStateContract;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

public class WebService {

	public static SoapObject invokeGetAutoConfigString(HashMap<String,String> map, String webMethName) {
		SoapObject response = null;
		// Create request
		SoapObject request = new SoapObject(Constants.CONSTANT_NAMESPACE, webMethName);
		// Property which holds input parameters
		for (Map.Entry<String, String> entry : map.entrySet()) {
			PropertyInfo propInfo = new PropertyInfo();
			propInfo.name=entry.getKey();
			propInfo.type= PropertyInfo.STRING_CLASS;
			request.addProperty(propInfo, entry.getValue());
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		String url = Utils.getUrl().trim();
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
