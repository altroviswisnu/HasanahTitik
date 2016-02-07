package com.altrovis.hasanahtitik.Business;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;

import java.util.ArrayList;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class LokasiRumahSakitHelper {

    public static ArrayList<LokasiRumahSakit> getAllRumahSakit(Context context) throws Exception {
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

}
