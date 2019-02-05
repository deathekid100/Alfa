package com.example.ernestoojea.app2;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by ernesto.ojea on 30/1/2019.
 */

public class SMSObserver extends ContentObserver {
    private String lastSmsId;
    private Context ctx;
    public SMSObserver(Handler handler,Context ctx) {
        super(handler);
        this.ctx = ctx;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Uri uriSMSURI = Uri.parse("content://sms/sent");
        Cursor cur = ctx.getContentResolver().query(uriSMSURI, null, null, null, null);
        cur.moveToNext();
        String id = cur.getString(cur.getColumnIndex("_id"));
        if (smsChecker(id)) {
            String address = cur.getString(cur.getColumnIndex("address"));
                String message = cur.getString(cur.getColumnIndex("body"));
                Sms x =  new Sms(message,address,"Saliente");
                MySqliteHandler db = new MySqliteHandler(ctx);
                db.addSms(x);
        }
    }

    // Prevent duplicate results without overlooking legitimate duplicates
    public boolean smsChecker(String smsId) {
        boolean flagSMS = true;

        if (smsId.equals(lastSmsId)) {
            flagSMS = false;
        }
        else {
            lastSmsId = smsId;
        }

        return flagSMS;
    }
}
