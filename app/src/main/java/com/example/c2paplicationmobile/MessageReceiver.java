package com.example.c2paplicationmobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;



public class MessageReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context,"Recibio el SMS",Toast.LENGTH_LONG).show();
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            String regEx = ":";
            final Object[] pdusObj = (Object[]) bundle.get("pdus");

            for (int j = 0; j < pdusObj.length; j++) {
                if (bundle != null){
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[j]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    try{
                        //if (senderNum.contains("4455")) {
                            Object[] pdus = (Object[]) bundle.get("pdus");
                            msgs = new SmsMessage[pdus.length];

                            for (int i = 0; i < msgs.length; i++) {
                                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                                msg_from = msgs[i].getOriginatingAddress();
                                String msgBody = msgs[i].getMessageBody();
                                String array[] = msgBody.split(regEx);
                                array[1] = array[1].trim();
                                String cod = array[1];

                                Toast.makeText(context, "clave: " + cod, Toast.LENGTH_LONG).show();
                            }
                        //}
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}