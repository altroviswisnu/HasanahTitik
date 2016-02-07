package com.altrovis.hasanahtitik.Business;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.altrovis.hasanahtitik.Entitties.HargaEmas;

import java.util.ArrayList;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class HargaEmasHelper {

    public static ArrayList<HargaEmas> getAllHargaEmas(Context context) throws Exception {
        ArrayList<HargaEmas> listOfEmas = new ArrayList<HargaEmas>();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.openDataBase();

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM HargaEmas";
        Cursor mCur = database.rawQuery(sql, null);
        if (mCur.moveToFirst()) {
            do {
                HargaEmas emas = new HargaEmas();
                emas.setID(mCur.getInt(0));
                emas.setNama(mCur.getString(1));
                //emas.setTanggal();
                emas.setHargaJual(mCur.getDouble(3));
                emas.setHargaBeli(mCur.getDouble(4));
                listOfEmas.add(emas);
            } while (mCur.moveToNext());
        }
        mCur.close();

        databaseHelper.close();

        return listOfEmas;
    }

}
