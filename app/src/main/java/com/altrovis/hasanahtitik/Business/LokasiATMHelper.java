package com.altrovis.hasanahtitik.Business;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.altrovis.hasanahtitik.Entitties.LokasiATM;

import java.util.ArrayList;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class LokasiATMHelper {

    public static ArrayList<LokasiATM> getAllATM(Context context) throws Exception {
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

}
