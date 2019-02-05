package com.example.ernestoojea.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ernesto.ojea on 29/1/2019.
 */

public class PackageResiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"cambio",Toast.LENGTH_LONG).show();
    }
}
