package com.alodiga.app.wallet.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.Settings;
import android.util.Base64;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alodiga.app.R;
import com.alodiga.app.wallet.encript.KeyLongException;
import com.alodiga.app.wallet.encript.S3cur1ty3Cryt3r;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class Utils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";


    //Fragments Tags
    public static final String Login_Fragment = "LoginFragment";
    public static final String SecurityQuestion_Fragment = "SecurityQuestionStep1Fragment";

    public static final String Welcome_Fragment = "RegisterStep4WelcomeFragment";
    public static final String Welcome_Secure_Fragment = "SecurityQuestionStep2WelcomeFragment";
    public static final String Welcome_Manual_Removal_Fragment = "Welcome_Manual_Removal_Fragment";
    public static final String register_step1_Fragment = "RegisterStep1Fragment";
    public static final String register_step2_Fragment = "RegisterStep2Fragment";
    public static final String register_step3_Fragment = "RegisterStep3Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String Register_step_1 = "register_step1_layout";
    public static final String Confirmation_Fragment = "Confirmation_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPasswordStep1Fragment";
    public static final String ForgotPasswordStep2_Fragment = "ForgotPasswordStep2Fragment";
    public static final String ForgotPasswordStep1_Fragment = "ForgotPasswordStep1Fragment";


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
     *
     * @param text parameter to be encrypted
     * @return encrypted parameter
     */
    public static String cryptoKey(String text) {
       // String secretKey = "1nt3r4xt3l3ph0nyDBWE";
        String base64EncryptedString = "";
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.MD5);
            byte[] digestOfPassword = md.digest(Constants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, Constants.K2_ENCRIPT_DESENCRIPT);
            Cipher cipher = Cipher.getInstance(Constants.K2_ENCRIPT_DESENCRIPT);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = text.getBytes(StandardCharsets.UTF_8);
            byte[] buf = cipher.doFinal(plainTextBytes);
            //byte[] base64Bytes = Base64.encodeBase64(buf);
            //base64EncryptedString = new String(base64Bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
        }
        return base64EncryptedString;
    }


    public static void createToast(@NonNull Context context, @NonNull int text) {
        LinearLayout layoutToast = new LinearLayout(context);
        //  layoutToast.setBackgroundResource(R.color.Toast_color);
        layoutToast.setBackgroundResource(R.color.colorPrimaryDark);
        layoutToast.setPadding(15, 15, 15, 15);
        layoutToast.setBackgroundColor(Color.argb(150, 0, 0, 0));
        TextView textViewToast = new TextView(context);
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 80);
        imageError.setLayoutParams(params);
        // add both the Views TextView and ImageView in layout
        layoutToast.addView(imageError);
        layoutToast.addView(textViewToast);
        Toast toast = new Toast(context); //context is object of Context write "this" if you are an Activity
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
    public static String getUrl(String namespace) {

        if (Constants.ALODIGA == namespace)
            return Constants.CONSTANT_IS_PRODUCTION ? Constants.CONSTANT_URL_PROD : Constants.CONSTANT_URL_QA_ALODIGA;
        else
            return Constants.CONSTANT_IS_PRODUCTION ? Constants.CONSTANT_URL_PROD : Constants.CONSTANT_URL_QA_REGISTRO_UNIFICADO;

    }


    public static String processPetition(SoapObject request, String url) throws Exception {
        String soapAction = " ";
        final String value;
        String response;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url.trim());
        if (!(envelope == null && soapAction == null && envelope.equals("") && soapAction.equals(""))) {
            androidHttpTransport.call(soapAction, envelope);
        }
        Object resultsRequestSOAP = envelope.getResponse();
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
            final String host = Constants.CONSTANT_IP;
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

    public static String formatNumber(String number) {
        double amount = Double.valueOf(number);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(amount);
    }

    public static StringBuilder formatNumberPhone(String numero) {
        StringBuilder numberPhone = new StringBuilder();
        numberPhone.append("+");
        numberPhone.append(numero.substring(0, 2));
        numberPhone.append(" ");
        numberPhone.append(numero.substring(2, 5));
        numberPhone.append("-");
        numberPhone.append(numero.substring(5));
        return numberPhone;
    }


    public static String getAndroidId(Context c) {
        return Settings.Secure.getString(c.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public static String aloDesencript(String clave) {
        try {
            clave = S3cur1ty3Cryt3r.aloDesencript(clave, Constants.KEY_ENCRIPT_DESENCRIPT, null, Constants.K2_ENCRIPT_DESENCRIPT, Constants.VECTOR_ENCRIPT_DESENCRIPT);
            //credencial = Utils.MD5(credencial);
            //System.out.println("MD5......................+ :"+ clave);
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (IllegalBlockSizeException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (NoSuchPaddingException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (BadPaddingException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (KeyLongException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        }
        return clave;
    }


    public static String aloEncrpter(String clave) {
        try {
            clave = S3cur1ty3Cryt3r.aloEncrpter(clave, Constants.KEY_ENCRIPT_DESENCRIPT, null, Constants.K2_ENCRIPT_DESENCRIPT, Constants.VECTOR_ENCRIPT_DESENCRIPT);
            //credencial = Utils.MD5(credencial);
            //System.out.println("MD5......................+ :"+ clave);
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (IllegalBlockSizeException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (NoSuchPaddingException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (BadPaddingException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (KeyLongException ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return Constants.WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO;
        }
        return clave;
    }


    public static String processPhone(String phone) {
        if (phone != null && phone.charAt(0) == '+')
            phone = phone.substring(1);
        return phone;
    }

    public static int progressBar(String newPassword)
    {
        Pattern capitalLetter = Pattern.compile(CAPITAL_LETTER);
        Matcher capLet = capitalLetter.matcher(newPassword);
        Pattern lowerLetter = Pattern.compile(LOWERCASE_LETTER);
        Matcher lowLet = lowerLetter.matcher(newPassword);
        Pattern number = Pattern.compile(NUMBER);
        Matcher nb = number.matcher(newPassword);
        Pattern specialCharacter = Pattern.compile(SPECIAL_CHARACTER);
        Matcher spChar = specialCharacter.matcher(newPassword);
        Pattern numberCharacters = Pattern.compile(NUMBER_OF_CHARACTERS);
        Matcher nmbChar = numberCharacters.matcher(newPassword);
        int progressIndicator = 0;

        boolean[]var = new boolean[]{false,false,false,false,false,false};

        if(!newPassword.equals(""))
        {
            if(capLet.lookingAt())
            {
                var[0]= true;
            }
            if(lowLet.lookingAt())
            {
                var[1]= true;
            }
            if(nb.lookingAt())
            {
                var[2]= true;
            }
            if(spChar.lookingAt())
            {
                var[3]= true;
            }
            if(nmbChar.lookingAt())
            {
                var[4]= true;
            }
            for(int i = 0; i< var.length ; i++)
            {
                if(var[i]==true)
                {
                    progressIndicator += 10;
                }
            }
            return progressIndicator;
        }else
        {
            return 0;
        }
    }

    public static int validatePassword(@NonNull String newPassword, @NonNull String confirmPassword)
    {
        Pattern capitalLetter = Pattern.compile(CAPITAL_LETTER);
        Matcher capLet = capitalLetter.matcher(newPassword);
        Pattern lowerLetter = Pattern.compile(LOWERCASE_LETTER);
        Matcher lowLet = lowerLetter.matcher(newPassword);
        Pattern number = Pattern.compile(NUMBER);
        Matcher nb = number.matcher(newPassword);
        Pattern specialCharacter = Pattern.compile(SPECIAL_CHARACTER);
        Matcher spChar = specialCharacter.matcher(newPassword);
        Pattern numberCharacters = Pattern.compile(NUMBER_OF_CHARACTERS);
        Matcher nmbChar = numberCharacters.matcher(newPassword);

        if(!capLet.lookingAt())
        {
            return R.string.validate_password_change_capital_letter;
        }else if(!lowLet.lookingAt())
        {
            return R.string.validate_password_change_lowercase_letter;
        }else if(!nb.lookingAt())
        {
            return R.string.validate_password_change_number;
        }else if(!spChar.lookingAt())
        {
            return R.string.validate_password_change_special_character;
        }else if(!nmbChar.lookingAt())
        {
            return R.string.validate_password_change_number_characters;
        }else if(!newPassword.equals(confirmPassword))
        {
            return R.string.toast_different_passwords;
        }
        return 0;
    }

    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public static boolean isNumeric_(String str) {
        return (str.matches("[+]?\\d*") && str.equals("")==false);
    }


    public static boolean updateResources(Context context, String language) {
        //Locale locale;//= Locale.getDefault();

        Locale locale = new Locale(language);
        Toast.makeText(context,locale.getCountry(),Toast.LENGTH_LONG);

        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }

    //convertir a base 64
    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String imgDecodableString = Base64.encodeToString(b, Base64.DEFAULT);

        return imgDecodableString;
    }


    public static Bitmap rotarBitmap(String Url, Bitmap bitmap) {
        try {
            ExifInterface exifInterface = new ExifInterface(Url);
            int orientacion = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();

            if (orientacion == 6) {
                matrix.postRotate(90);
            } else if (orientacion == 3) {
                matrix.postRotate(180);
            } else if (orientacion == 8) {
                matrix.postRotate(270);
            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true); // rotating bitmap
        } catch (Exception e) {
            // TODO:
        }
        return bitmap;
    }

    public static String mask_card(String card){
        card= card.substring(0,4) + "*********" +  card.substring( card.length()-4, card.length());
        return card;
    }

}
