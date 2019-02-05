package com.example.ernestoojea.app2;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.widget.Toast;

public class CallReceiver extends PhonecallReceiver {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"LLamada entrante",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"LLamada saliente",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx,"LLamada entrante de: "+number,Toast.LENGTH_SHORT).show();
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm");
        String dateString = formater.format(start);
        Call add = new Call(number,"Entrante",dateString,(int)(end.getTime()-start.getTime())/1000);
        MySqliteHandler bd = new MySqliteHandler(ctx);
        bd.addCall(add);

    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx,"LLamada saliente a: "+number,Toast.LENGTH_SHORT).show();

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm");
        String dateString = formater.format(start);
        Call add = new Call(number,"Saliente",dateString,(int)(end.getTime()-start.getTime())/1000);
        MySqliteHandler bd = new MySqliteHandler(ctx);
        bd.addCall(add);

    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Toast.makeText(ctx,"LLamada perdida de: " + number,Toast.LENGTH_SHORT).show();
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm");
        String dateString = formater.format(start);
        Call add = new Call(number,"Perdida",dateString,0);
        MySqliteHandler bd = new MySqliteHandler(ctx);
        bd.addCall(add);
    }

}