package com.altrovis.hasanahtitik.Business;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by ricki_000 on 23/07/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "DatabaseHelper"; // Tag just for the LogCat window
    private static String DB_PATH = ""; //destination path (location) of our database on device
    private static String DB_NAME = "HasanahTitik.db";// Database name
    private final Context mContext;
    private SQLiteDatabase mDataBase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1); // 1? its Database Version
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void forceCreateDatabase() throws IOException {

        this.getReadableDatabase();
        this.close();
        try {
            //Copy the database from assets
            copyDataBase();
        } catch (IOException mIOException) {
            mIOException.printStackTrace();
        }
    }

    public void createDataBase() throws IOException {
        //If database not exists copy it from the assets

        boolean isDataBaseExist = checkDataBase();
        if (!isDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assets
                copyDataBase();
            } catch (IOException mIOException) {
                mIOException.printStackTrace();
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/DBName
    public boolean checkDataBase() throws IOException {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //Copy the database from assets
    public void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
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

    }
}
