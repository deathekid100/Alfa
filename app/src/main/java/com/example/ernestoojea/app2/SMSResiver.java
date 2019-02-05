package com.example.ernestoojea.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by ernesto.ojea on 30/1/2019.
 */

public class SMSResiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // get sms objects
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0) {
                return;
            }
            // large message might be broken into many
            SmsMessage[] messages = new SmsMessage[pdus.length];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sb.append(messages[i].getMessageBody());
            }
            String address = messages[0].getOriginatingAddress();
            String message = sb.toString();
            Log.i("msg","sms entrante");
            Sms x= new Sms(message,address,"Entrante");
            MySqliteHandler db = new MySqliteHandler(context);
            db.addSms(x);
            // prevent any other broadcast receivers from receiving broadcast
            // abortBroadcast();
        }
    }
}
