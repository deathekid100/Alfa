package com.example.ernestoojea.app2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MySqliteHandler extends SQLiteOpenHelper{
    private static String DB_NAME = "app.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 2;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public MySqliteHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
        Log.i("update","db");
    }

    public void addCall(Call call){
        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("number",call.getNumber());
        values.put("type",call.getType());
        values.put("date",call.getDate().toString());
        values.put("duration",call.getDuration());
        database.insert("call",null,values);
        database.close();
        Log.i("msg","add call");

    }
    public Call getCall(int id){
        SQLiteDatabase database = MySqliteHandler.this.getReadableDatabase();
        Cursor cursor = database.query("call",new String[]{"id","number","type","date","duration"}, "id=?",new String[]{String.valueOf(id)},null,null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        else{
            return new Call();
        }
        Call x = new Call(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),Integer.parseInt(cursor.getString(4)));
        return x;
    }
    public ArrayList<Call> getAllElements() {

        ArrayList<Call> list = new ArrayList<Call>();

        // Select All Query
        String selectQuery = "SELECT  * FROM call";

        SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            while(cursor.moveToNext()){
                Call x = new Call(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),Integer.parseInt(cursor.getString(4)));
                //only one column
                list.add(x);
            }
               cursor.close();
                db.close();

        return list;
    }


    public void addSms(Sms sms){
        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("body",sms.getBody());
        values.put("address",sms.getAddress());
        values.put("type",sms.getType());
        values.put("date",sms.getDate());
        database.insert("sms",null,values);
        database.close();
        Log.i("msg","add sms");

    }
    public ArrayList<Sms> getAllElementsSms() {

        ArrayList<Sms> list = new ArrayList<Sms>();

        // Select All Query
        String selectQuery = "SELECT  * FROM sms";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            Sms x = new Sms(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            //only one column
            list.add(x);
        }
        cursor.close();
        db.close();

        return list;
    }
    public void addScreen(Screen screen){
        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date",screen.getDate());
        values.put("type",screen.getType());
        values.put("islock",screen.getIslock());
        database.insert("screen",null,values);
        database.close();
        Log.i("msg","add screen");

    }
    public ArrayList<Screen> getAllElementsScreen() {
        ArrayList<Screen> list = new ArrayList<Screen>();
        String selectQuery = "SELECT  * FROM screen";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            Screen x = new Screen(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
            list.add(x);
        }
        cursor.close();
        db.close();
        return list;
    }






}
