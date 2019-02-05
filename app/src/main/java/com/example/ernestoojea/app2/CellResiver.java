package com.example.ernestoojea.app2;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CellResiver {

    TelephonyManager telephony;
    Context context;
    int CID,LAC;
    public CellResiver(Context context) {

        telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        this.context = context;
    }
    public void Task(){
        int cid=0,lac=0;
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
            if (location != null) {
                cid = location.getCid();
                lac = location.getLac();

            }
            else{
               // Log.i("error","gsm");
            }
        }
        else if(telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA){
            final CdmaCellLocation location = (CdmaCellLocation) telephony.getCellLocation();
            if (location != null) {
                cid = location.getBaseStationId();
                lac = location.getSystemId();
            }
            else{
                //Log.i("error","cdma");
            }
        }
       // Log.i("msg","ID=>"+cid +" LAC=>"+lac);

        if(CID!=cid && LAC!=lac){//insertar new y actualizar
            //insertar
            Toast.makeText(context,"Cambio de Celda",Toast.LENGTH_SHORT).show();

            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            String dateString = formater.format(new Date());
            Tower tower = new Tower(dateString,cid,lac);
            MySqliteHandler bd = new MySqliteHandler(context);
            bd.addTower(tower);
            //actualizar
            CID = cid;
            LAC = lac;
        }
        //si no esta en la misma celda id
    }

}
