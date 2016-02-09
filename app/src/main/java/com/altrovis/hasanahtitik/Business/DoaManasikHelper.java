package com.altrovis.hasanahtitik.Business;

import com.altrovis.hasanahtitik.Entitties.DoaManasik;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class DoaManasikHelper {

    private static String TAG_ID = "ID";
    private static String TAG_JUDUL = "Judul";
    private static String TAG_URL = "Url";

    public static ArrayList<DoaManasik> getListOfDoaManasik(String url) throws Exception {

        JSONArray arrayOfDoaManasik = GlobalFunction.GetJSONArray(url);
        ArrayList<DoaManasik> listOfDoaManasik =  new ArrayList<DoaManasik>();

        if (arrayOfDoaManasik != null) {
            if (arrayOfDoaManasik.length() > 0) {
                for (int j = 0; j < arrayOfDoaManasik.length(); j++) {
                    JSONObject detailDoaManasik = arrayOfDoaManasik.getJSONObject(j);

                    DoaManasik doaManasik = new DoaManasik();
                    doaManasik.setID(detailDoaManasik.getInt(TAG_ID));
                    doaManasik.setNama(detailDoaManasik.getString(TAG_JUDUL));
                    doaManasik.setUrlWebView(URLDecoder.decode(detailDoaManasik.getString(TAG_URL), "UTF-8"));
                    listOfDoaManasik.add(doaManasik);
                }
            }
        }

        return listOfDoaManasik;
    }

}
