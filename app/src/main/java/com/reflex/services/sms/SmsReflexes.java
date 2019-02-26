package com.reflex.services.sms;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.reflex.core.providers.ReflexProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Objects;


class SmsReflexes extends ReflexProvider {

    private static final String TAG =
            SmsReflexes.class.getSimpleName();
    private static final String pdu_type = "pdus";

    private static ReflexProvider instance;

    public static ReflexProvider getInstance() {
        if (instance == null) {
            instance = new SmsReflexes();
        }
        return instance;
    }

    private SmsReflexes() {

        map = new HashMap<>();
        internalMap = new HashMap<>();

        internalMap.put(READ_SMS_FROM_PROVIDER, args -> {
            Intent intent = (Intent) args[0];
            JSONObject callback = (JSONObject) args[1];
            readSMSFromIntent(intent, callback);
        });

        internalMap.put(FILTER_SMS_FROM_PROVIDER, args -> {
            Intent intent = (Intent) args[0];
            ObjectNode filters = (ObjectNode) args[1];
            ObjectNode callback = (ObjectNode) args[2];
            filterSMSIntent(intent, filters, callback);
        });

        map.put(SEND_SMS_TEXT_MESSAGE, args -> {
            String dAddress = (String) args[0];
            String scAddress= (String) args[1];
            String message = (String) args[2];
            sendTextMessage(dAddress, scAddress, message);
        });
    }


    private void readSMSFromIntent(Intent intent, JSONObject resultCallBack) {

        // Get the SMS message.
        Log.wtf("Function SMS Read","executing");
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        StringBuilder strMessage = new StringBuilder();
        assert bundle != null;
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);

        try {
            JSONArray resultArray = new JSONArray();

            if (pdus != null) {
                Log.wtf(TAG, "onReceive: Recieved");
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
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
                    strMessage.append("SMS from ").append(msgs[i].getOriginatingAddress());
                    strMessage.append(" :").append(msgs[i].getMessageBody()).append("\n");
                    // Log and display the SMS message.
                    JSONObject messageJson = new JSONObject();

                    messageJson.put("number",msgs[i].getOriginatingAddress());
                    messageJson.put("message",msgs[i].getOriginatingAddress());
                    resultArray.put(messageJson);
                }
                Log.wtf(TAG, "onReceive: " + strMessage);
                resultCallBack.put("results",resultArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void filterSMSIntent(Intent intent, ObjectNode filterArgs, ObjectNode resultCallBack) {
        // Get the SMS message.
        resultCallBack.put("matched",false);
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        StringBuilder strMessage = new StringBuilder();
        assert bundle != null;
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        String phoneNumber = filterArgs.get("number").asText();
        String filterMessage = filterArgs.get("message").asText();
            if (pdus != null) {
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    if (phoneNumber != null && !Objects.requireNonNull(msgs[i].getOriginatingAddress()).equalsIgnoreCase(phoneNumber)) {
                        return;
                    }
                    // Build the message to show.
                    strMessage.append(msgs[i].getMessageBody());
                }

                if (filterMessage != null && filterMessage.equals(strMessage.toString())) {
                    resultCallBack.put("matched",true);
                    return;
                }
            }
    }


    private void sendTextMessage(String destinationAddress, String scAddress, String text) {

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(destinationAddress, scAddress, text, null , null);
    }
}
