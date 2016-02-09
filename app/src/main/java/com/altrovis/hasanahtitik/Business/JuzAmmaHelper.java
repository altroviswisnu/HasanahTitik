package com.altrovis.hasanahtitik.Business;

import com.altrovis.hasanahtitik.Entitties.JuzAmma;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class JuzAmmaHelper {

    private static String TAG_ID = "ID";
    private static String TAG_JUDUL = "Judul";
    private static String TAG_URL = "Url";

    public static ArrayList<JuzAmma> getListOfJuzAmma(String url) throws Exception {

        JSONArray arrayOfJuzAmma = GlobalFunction.GetJSONArray(url);
        ArrayList<JuzAmma> listOfJuzAmma =  new ArrayList<JuzAmma>();

        if (arrayOfJuzAmma != null) {
            if (arrayOfJuzAmma.length() > 0) {
                for (int j = 0; j < arrayOfJuzAmma.length(); j++) {
                    JSONObject detailJuzAmma = arrayOfJuzAmma.getJSONObject(j);

                    JuzAmma juzAmma = new JuzAmma();
                    juzAmma.setID(detailJuzAmma.getInt(TAG_ID));
                    juzAmma.setNama(detailJuzAmma.getString(TAG_JUDUL));
                    juzAmma.setUrlWebView(URLEncoder.encode(detailJuzAmma.getString(TAG_URL),"UTF-8"));
                    listOfJuzAmma.add(juzAmma);
                }
            }
        }

        return listOfJuzAmma;
    }

}
