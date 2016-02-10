package com.altrovis.hasanahtitik.Business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class LokasiRumahSakitHelper {

    private static String TAG_ID = "ID";
    private static String TAG_NAMA = "Nama";
    private static String TAG_ALAMAT = "Alamat";
    private static String TAG_LATITUDE = "Latitude";
    private static String TAG_LONGITUDE = "Longitude";

    public static ArrayList<LokasiRumahSakit> getListOfRumahSakitFromWebService(Context context, String url) throws Exception {

        JSONArray arrayOfRumahSakit = GlobalFunction.GetJSONArray(url);

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        if (arrayOfRumahSakit != null) {
            if (arrayOfRumahSakit.length() > 0) {
                for (int j = 0; j < arrayOfRumahSakit.length(); j++) {
                    JSONObject detailRumahSakit = arrayOfRumahSakit.getJSONObject(j);

                    ContentValues values = new ContentValues();
                    values.put(TAG_ID, detailRumahSakit.getInt(TAG_ID));
                    values.put(TAG_NAMA, detailRumahSakit.getString(TAG_NAMA));
                    values.put(TAG_ALAMAT, detailRumahSakit.getString(TAG_ALAMAT));
                    values.put(TAG_LATITUDE, detailRumahSakit.getDouble(TAG_LATITUDE));
                    values.put(TAG_LONGITUDE, detailRumahSakit.getDouble(TAG_LONGITUDE));
                    database.insert("LokasiRumahSakit", null, values);

                }
            }
        }

        databaseHelper.close();
        return getListOfRumahSakitFromDataBase(context);
    }


    public static ArrayList<LokasiRumahSakit> getListOfRumahSakitFromDataBase(Context context) throws Exception {
        ArrayList<LokasiRumahSakit> listOfRumahSakit = new ArrayList<LokasiRumahSakit>();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM LokasiRumahSakit";
        Cursor mCur = database.rawQuery(sql, null);
        if (mCur.moveToFirst()) {
            do {
                LokasiRumahSakit rumahSakit = new LokasiRumahSakit();
                rumahSakit.setID(mCur.getInt(0));
                rumahSakit.setNama(mCur.getString(1));
                rumahSakit.setAlamat(mCur.getString(2));
                rumahSakit.setLatitude(mCur.getDouble(3));
                rumahSakit.setLongitude(mCur.getDouble(4));
                listOfRumahSakit.add(rumahSakit);
            } while (mCur.moveToNext());
        }
        mCur.close();

        databaseHelper.close();

        return listOfRumahSakit;
    }

    public static boolean isEmpty(Context context) throws Exception {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String sql = "SELECT count(*) FROM LokasiRumahSakit";
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

        database.execSQL("DELETE FROM LokasiRumahSakit");

        databaseHelper.close();
    }

}
