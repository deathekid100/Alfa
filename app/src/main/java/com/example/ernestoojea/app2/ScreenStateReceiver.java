package com.example.ernestoojea.app2;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ernesto.ojea on 4/2/2019.
 */
public class ScreenStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String dateString = formater.format(new Date());
        String strAction = intent.getAction();
        MySqliteHandler bd = new MySqliteHandler(context);
        KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if(strAction.equals(Intent.ACTION_USER_PRESENT) || strAction.equals(Intent.ACTION_SCREEN_OFF) || strAction.equals(Intent.ACTION_SCREEN_ON)  )

            if(strAction.equals(Intent.ACTION_SCREEN_OFF)){
                System.out.println("Screen off");
                Screen screen =  new Screen(dateString,"off",1);
                bd.addScreen(screen);
            }
            else if(strAction.equals(Intent.ACTION_SCREEN_ON)){
                if( myKM.inKeyguardRestrictedInputMode())
                {
                    System.out.println("Screen on " + "LOCKED");
                    Screen screen =  new Screen(dateString,"on",1);
                    bd.addScreen(screen);
                } else
                {
                    System.out.println("Screen on " + "UNLOCKED");
                    Screen screen =  new Screen(dateString,"on",0);
                    bd.addScreen(screen);
                }

            }
            else{
                System.out.println("Screen on " + "UNLOCKED");
                Screen screen =  new Screen(dateString,"on",0);
                bd.addScreen(screen);
            }




    }
}
