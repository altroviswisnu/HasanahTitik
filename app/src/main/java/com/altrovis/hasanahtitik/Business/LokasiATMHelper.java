package com.altrovis.hasanahtitik.Business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.altrovis.hasanahtitik.Entitties.LokasiATM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class LokasiATMHelper {

    private static String TAG_ID = "ID";
    private static String TAG_NAMA = "Nama";
    private static String TAG_ALAMAT = "Alamat";
    private static String TAG_LATITUDE = "Latitude";
    private static String TAG_LONGITUDE = "Longitude";

    public static ArrayList<LokasiATM> getListOfATMFromWebService(Context context, String url) throws Exception {

        JSONArray arrayOfATM = GlobalFunction.GetJSONArray(url);

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        if (arrayOfATM != null) {
            if (arrayOfATM.length() > 0) {
                for (int j = 0; j < arrayOfATM.length(); j++) {
                    JSONObject detailATM = arrayOfATM.getJSONObject(j);

                    ContentValues values = new ContentValues();
                    values.put(TAG_ID, detailATM.getInt(TAG_ID));
                    values.put(TAG_NAMA, detailATM.getString(TAG_NAMA));
                    values.put(TAG_ALAMAT, detailATM.getString(TAG_ALAMAT));
                    values.put(TAG_LATITUDE, detailATM.getDouble(TAG_LATITUDE));
                    values.put(TAG_LONGITUDE, detailATM.getDouble(TAG_LONGITUDE));
                    database.insert("LokasiATM", null, values);

                }
            }
        }

        databaseHelper.close();
        return getListOFATMFromDatabase(context);
    }


    public static ArrayList<LokasiATM> getListOFATMFromDatabase(Context context) throws Exception {
        ArrayList<LokasiATM> listOfATM = new ArrayList<LokasiATM>();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM LokasiATM";
        Cursor mCur = database.rawQuery(sql, null);
        if (mCur.moveToFirst()) {
            do {
                LokasiATM atm = new LokasiATM();
                atm.setID(mCur.getInt(0));
                atm.setNama(mCur.getString(1));
                atm.setAlamat(mCur.getString(2));
                atm.setLatitude(mCur.getDouble(3));
                atm.setLongitude(mCur.getDouble(4));
                listOfATM.add(atm);
            } while (mCur.moveToNext());
        }
        mCur.close();

        databaseHelper.close();

        return listOfATM;
    }

    public static boolean isEmpty(Context context) throws Exception {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String sql = "SELECT count(*) FROM LokasiATM";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        databaseHelper.close();

        return count <= 0;
    }

    public static void deleteAllRumahSakit(Context context) throws Exception {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        database.execSQL("DELETE FROM LokasiATM");

        databaseHelper.close();
    }


}
