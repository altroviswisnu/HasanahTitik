package com.altrovis.hasanahtitik.Business;

import com.altrovis.hasanahtitik.Entitties.DoaHarian;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class DoaHarianHelper {

    private static String TAG_ID = "ID";
    private static String TAG_JUDUL = "Judul";
    private static String TAG_URL = "Url";

    public static ArrayList<DoaHarian> getListOfDoaHarian(String url) throws Exception {

        JSONArray arrayOfDoaHarian = GlobalFunction.GetJSONArray(url);
        ArrayList<DoaHarian> listOfDoaHarian =  new ArrayList<DoaHarian>();

        if (arrayOfDoaHarian != null) {
            if (arrayOfDoaHarian.length() > 0) {
                for (int j = 0; j < arrayOfDoaHarian.length(); j++) {
                    JSONObject detailDoaHarian = arrayOfDoaHarian.getJSONObject(j);

                    DoaHarian doaHarian = new DoaHarian();
                    doaHarian.setID(detailDoaHarian.getInt(TAG_ID));
                    doaHarian.setNama(detailDoaHarian.getString(TAG_JUDUL));
                    doaHarian.setUrlWebView(URLEncoder.encode(detailDoaHarian.getString(TAG_URL), "UTF-8"));
                    listOfDoaHarian.add(doaHarian);
                }
            }
        }

        return listOfDoaHarian;
    }

}
