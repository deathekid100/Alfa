package com.example.ernestoojea.app2;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Call {
    private  int id;
    private String number;
    private String type;
    private String date;
    private Integer duration;

    public Call(){
        super();
    }

    public Call(int id ,String number, String type, String date,Integer duration) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.duration = duration;
        this.date = date;
    }
    public Call(String number, String type, String date,Integer duration) {

        this.number = number;
        this.type = type;
        this.duration = duration;
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static void LogCall(Context ctx){


        Cursor managedCursor = ctx.getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null,null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int id = managedCursor.getColumnIndex(CallLog.Calls._ID);
         managedCursor.moveToLast();
            String phNumber =  managedCursor.getString(number);
            String callType =  managedCursor.getString(type);
            String callDate =  managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy HH:mm");
            String dateString = formater.format(callDayTime);
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int idx = Integer.parseInt(managedCursor.getString(id));
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Saliente";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Entrante";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Perdida";
                    break;
            }
            Call xx = new Call(idx,phNumber,dir,dateString,Integer.parseInt(callDuration));
            MySqliteHandler mySqliteHandler = new MySqliteHandler(ctx);
            mySqliteHandler.addCall(xx);
        Log.i("a",phNumber);

        managedCursor.close();
        Toast.makeText(ctx,"llamada registrada correctamente",Toast.LENGTH_LONG).show();
    }



}
