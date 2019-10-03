package com.example.c2paplicationmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

//import org.apache.commons.codec.binary.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
	
	//Email Validation pattern
	public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";



	//Fragments Tags
	public static final String Login_Fragment = "Login_Fragment";
	public static final String SecurityQuestion_Fragment = "SecurityQuestion_Fragment";

	public static final String Welcome_Fragment = "Welcome_Fragment";
	public static final String Welcome_Secure_Fragment = "Welcome_Secure_Fragment";
	public static final String Welcome_Manual_Removal_Fragment = "Welcome_Manual_Removal_Fragment";
	public static final String register_step1_Fragment = "RegisterStep1_Fragment";
	public static final String register_step2_Fragment = "RegisterStep2_Fragment";
	public static final String register_step3_Fragment = "RegisterStep3_Fragment";
	public static final String SignUp_Fragment = "SignUp_Fragment";
	public static final String Register_step_1 = "register_step1_layout";
	public static final String Confirmation_Fragment = "Confirmation_Fragment";
	public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";

	/**
	 * CAPITAL_LETTER: Constant used to detect the existence of a capital letter in a text string
	 */
	private static final String CAPITAL_LETTER = "(?=.*?[A-Z])";
	/**
	 * LOWERCASE_LETTER: Constant used to detect the existence of a lowercase letter in a text string
	 */
	private static final String LOWERCASE_LETTER = "(?=.*?[a-z])";
	/**
	 * NUMBER: Constant used to detect the existence of a number in a text string
	 */
	private static final String NUMBER = "(?=.*?[0-9])";
	/**
	 * SPECIAL_CHARACTER: Constant used to detect the existence of a special character in a text string
	 */
	private static final String SPECIAL_CHARACTER = "(?=.*?[#?!@$%^&*-])";
	/**
	 * NUMBER_OF_CHARACTERS: Constant used to detect the number of characters allowed in a text string
	 */
	private static final String NUMBER_OF_CHARACTERS = ".{8,}";
	/**
	 * TEXT_SIZE: Constant used to indicate letter size
	 */
	private static final int TEXT_SIZE = 15;




	/**
	 * method used to encrypt the parameters sent to the webservice
	 * @param text parameter to be encrypted
	 * @return encrypted parameter
	 */
	public static String cryptoKey(String text) {
		String secretKey = "1nt3r4xt3l3ph0nyDBWE";
		String base64EncryptedString = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainTextBytes = text.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			//byte[] base64Bytes = Base64.encodeBase64(buf);
			//base64EncryptedString = new String(base64Bytes);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
		}
		return base64EncryptedString;
	}


	public static void createToast(@NonNull Context context, @NonNull int text)
	{
		LinearLayout layoutToast = new LinearLayout(context);
		//  layoutToast.setBackgroundResource(R.color.Toast_color);
		layoutToast.setBackgroundResource(R.color.colorPrimaryDark);
		layoutToast.setPadding(15, 15, 15, 15);
		layoutToast.setBackgroundColor(Color.argb(150, 0, 0, 0));
		TextView textViewToast=new TextView(context);
		// set the TextView properties like color, size etc
		textViewToast.setWidth(350);
		textViewToast.setTextColor(Color.parseColor("#ef9a9a"));
		textViewToast.setTextSize(TEXT_SIZE);
		textViewToast.setGravity(Gravity.CENTER_VERTICAL);
		textViewToast.setPadding(15, 15, 15, 15);
		// set the text you want to show in  Toast
		textViewToast.setText(text);
		// textViewToast.setPadding(10,10,5,10);
		ImageView imageError = new ImageView(context);
		// give the drawble resource for the ImageView
		imageError.setImageResource(R.drawable.alocoinlogo);
		LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(80,80);
		imageError.setLayoutParams(params);
		// add both the Views TextView and ImageView in layout
		layoutToast.addView(imageError);
		layoutToast.addView(textViewToast);
		Toast toast=new Toast(context); //context is object of Context write "this" if you are an Activity
		// Set The layout as Toast View
		toast.setView(layoutToast);
		// Position you toast here toast position is 50 dp from bottom you can give any integral value
		toast.setGravity(Gravity.TOP, 0, 80);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

	/*public static String getUrl(){
		return Constants.CONSTANT_IS_PRODUCTION ?  Constants.CONSTANT_SECURE_URL : Constants.CONSTANT_URL_PROD ;
	}*/
	public static String getUrl(String namespace){

	if(Constants.ALODIGA==namespace)
		return Constants.CONSTANT_IS_PRODUCTION ?  Constants.CONSTANT_URL_PROD: Constants.CONSTANT_URL_QA_ALODIGA;
	else
		return Constants.CONSTANT_IS_PRODUCTION ? Constants.CONSTANT_URL_PROD:  Constants.CONSTANT_URL_QA_REGISTRO_UNIFICADO;

	}



	public static String processPetition(SoapObject request, String url) throws Exception
	{
		String soapAction = " ";
		final String value;
		String response;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url.trim());
		if (!(envelope == null && soapAction == null && envelope.equals("")  && soapAction.equals("") ))
		{
			androidHttpTransport.call(soapAction, envelope);
		}
		Object resultsRequestSOAP = (Object) envelope.getResponse();
		value = resultsRequestSOAP.toString();
		return value;
	}

	public static String getIP() {
		List<InetAddress> addrs;
		String address = "";
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
						address = addr.getHostAddress().toUpperCase(new Locale("es", "MX"));
					}
				}
			}
		} catch (Exception e) {
			// Log.w(TAG, "Ex getting IP value " + e.getMessage());
		}
		return address;
	}

	public static String getConnectionStatus() {
		String conStatus = null;
		try {
			final String host= Constants.CONSTANT_IP;
			int timeOut = 5000;
			InetAddress address = InetAddress.getByName(host);

			if (address.isReachable(timeOut))
				conStatus = "Online";
			else
				conStatus = "Offline";
		} catch (Exception e) {
			conStatus = "Offline";
		}
		return conStatus;
	}

	public static String formatNumber(String number){
		double amount = Double.valueOf(number);
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		return formatter.format(amount);
	}

	public static StringBuilder formatNumberPhone(String numero){
		StringBuilder numberPhone = new StringBuilder();
		numberPhone.append("+");
		numberPhone.append(numero.substring(0,2));
		numberPhone.append(" ");
		numberPhone.append(numero.substring(2,5) );
		numberPhone.append("-");
		numberPhone.append(numero.substring(5));
		return numberPhone;
	}


	public  static String getAndroidId(Context c){
		return Settings.Secure.getString(c.getContentResolver(),
				Settings.Secure.ANDROID_ID);
	}

	
}
