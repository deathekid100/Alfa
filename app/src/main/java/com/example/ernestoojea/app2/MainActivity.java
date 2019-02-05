package com.example.ernestoojea.app2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MySqliteHandler mySqliteHandler;
    ScreenStateReceiver mScreenStateReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms"), true, new SMSObserver(new Handler(),this));
        mySqliteHandler = new MySqliteHandler(MainActivity.this);
        //code screen resiver registry
        mScreenStateReceiver = new ScreenStateReceiver();
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenStateFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenStateReceiver, screenStateFilter);



        //code Tower timer
        Timer timer = new Timer();
        final Handler handler = new Handler();
        final CellResiver cellResiver = new CellResiver(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                AsyncTask mytask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        new Handler (Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                cellResiver.Task();
                            }
                        });

                        return null;
                    }
                };
                mytask.execute();
            }
        };
        timer.schedule(task,0,3000);



        //pedir permiso
       /* if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CALL_LOG)){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
            }
        }
        else{
            TextView tx = (TextView)findViewById(R.id.textView);

            Button btnAdd = (Button)findViewById(R.id.button);
            Button btn2 = (Button)findViewById(R.id.button2);
            btnAdd.setOnClickListener(MainActivity.this);
            btn2.setOnClickListener(MainActivity.this);
            //Toast.makeText(this,String.valueOf(yy.getType()),Toast.LENGTH_SHORT).show();
        }*/



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenStateReceiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"conf",Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        TextView tx = (TextView)findViewById(R.id.textView);
        if (id == R.id.call) {

            //tx.setText("");
            ArrayList<Call> n = mySqliteHandler.getAllElements();
            String x = "LLamadas Registradas\n----------------------\n";

            for (int i = n.size()-1;i>=0;i--){
                x+="Número: "+ n.get(i).getNumber() +"\n";
                x+="Tipo: "+ n.get(i).getType() +"\n";
                x+="Fecha: "+ n.get(i).getDate() +"\n";
                x+="Duración: "+ n.get(i).getDuration() +"s\n";
                x+="----------------------\n";

            }
            x+="Total de llamadas: "+String.valueOf(n.size())+"\n";
            tx.setText(x);
        }
        else if (id == R.id.sms) {
            ArrayList<Sms>n = mySqliteHandler.getAllElementsSms();
            String x = "SMS Registrados\n----------------------\n";

            for (int i = n.size()-1;i>=0;i--){
                x+="Número: "+ n.get(i).getAddress() +"\n";
                x+="Tipo: "+ n.get(i).getType() +"\n";
                x+="Fecha: "+ n.get(i).getDate() +"\n";
                x+="Tamaño: " + n.get(i).getBody().length()+"\n";
                x+="Texto: "+ n.get(i).getBody() +"\n";
                x+="----------------------\n";

            }
            x+="Total de SMS: "+String.valueOf(n.size())+"\n";
            tx.setText(x);
        }
        else if (id == R.id.pantalla){
            int on=0,off=0,lock=0;
            String x = "";
            List<Screen> screens = mySqliteHandler.getAllElementsScreen();
            if(screens.size()>0) {

                for (int i = screens.size() - 1; i >= 0; i--) {
                    if (screens.get(i).getType().equals("off")) {
                        x += screens.get(i).getDate() + " Pantalla apagada\n";
                    } else {
                        if (screens.get(i).getIslock() == 1) {
                            x += screens.get(i).getDate() + " Pantalla encendida (Bloqueada)\n";
                        } else {
                            x += screens.get(i).getDate() + " Pantalla encendida (Desbloqueada)\n";
                        }
                    }
                }
                String current = screens.get(0).getType();
                Screen last = screens.get(0);
                DateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                // Date date = format.parse(string);
                try {
                    for (int i = 1; i < screens.size(); i++) {
                        if (current.equals("on") && last.getIslock() == 1) {
                            if (screens.get(i).getType().equals("off")) {//onLock para off
                                Date lx = format.parse(last.getDate());
                                Date fina = format.parse(screens.get(i).getDate());
                                long time = (fina.getTime() - lx.getTime());
                                Log.i("t", String.valueOf(time));
                                on += time;
                                lock += time;
                            } else if (screens.get(i).getType().equals("on") && screens.get(i).getIslock() == 0) {//onLock para onunlock
                                Date lx = format.parse(last.getDate());
                                Date fina = format.parse(screens.get(i).getDate());
                                long time = (fina.getTime() - lx.getTime());
                                on += time;
                                lock += time;
                                Log.i("t", String.valueOf(time));
                            }
                        } else if (current.equals("on") && last.getIslock() == 0) {
                            if (screens.get(i).getType().equals("off")) {//onLock para off
                                Date lx = format.parse(last.getDate());
                                Date fina = format.parse(screens.get(i).getDate());
                                long time = (fina.getTime() - lx.getTime());
                                on += time;
                                Log.i("t", String.valueOf(time));
                            }
                        } else if (current.equals("off")) {//off para on
                            if (screens.get(i).getType().equals("on")) {//off++
                                Date lx = format.parse(last.getDate());
                                Date fina = format.parse(screens.get(i).getDate());
                                long time = (fina.getTime() - lx.getTime());
                                off += time;
                                Log.i("t", String.valueOf(time));
                            }
                        }
                        last = screens.get(i);
                        current = screens.get(i).getType();
                    }
                } catch (Exception e) {
                    Log.i("errror", "error");
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                }
            }
            String inicio = "Pantalla Encendida: " + TimeCount.toLongTime(on/1000) + "\n"+
                            "Pantalla Encendida (Bloqueada): " + TimeCount.toLongTime(lock/1000) + "\n"+
                            "Pantalla Apagada: "+TimeCount.toLongTime(off/1000)+"\n-----------------------\n";
            tx.setText(inicio+x);
        }
        else if(id == R.id.celda){
            String x="Celda actual:\n";
            int cid=0;
            int lac=0;
            final TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
                if (location != null) {
                    cid = location.getCid();
                    lac = location.getLac();
                }
            }
            else if(telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA){
                final CdmaCellLocation location = (CdmaCellLocation) telephony.getCellLocation();
                if (location != null) {
                    cid = location.getBaseStationId();
                    lac = location.getSystemId();
                }
            }
            x+="Celda LAC: " + lac + " Celda ID: " + cid+"\n";
            x+="------------------------------------------------------------------\n";
            x+="Celdas utilizadas\n";
            x+="------------------------------------------------------------------\n";
            List<Tower>uniqueTower = mySqliteHandler.getAllUniqueTower();
            List<Tower>allTower = mySqliteHandler.getAllElementsTower();
            for(int i=0;i<uniqueTower.size();i++){
                x+="Celda ID: "+uniqueTower.get(i).getCid()+"  "+"Celda LAC: "+uniqueTower.get(i).getLac()+"\n";
                x+="Última vez usado: "+uniqueTower.get(i).getDate()+"\n";
                x+="------------------------------------------------------------------\n";
            }
            x+="Registro de Torres\n";

            for(int i=0;i<allTower.size();i++){
                x+="------------------------------------------------------------------\n";
                x+="Celda ID: "+allTower.get(i).getCid()+"  "+"Celda LAC: "+allTower.get(i).getLac()+"\n";
                x+="Última vez usado: "+allTower.get(i).getDate()+"\n";
            }
            x+="------------------------------------------------------------------\n";
            tx.setText(x);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
