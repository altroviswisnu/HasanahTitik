package com.altrovis.hasanahtitik.Business;

import com.altrovis.hasanahtitik.Entitties.HasanahPromo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class HasanahPromoHelper {

    private static String TAG_ID = "ID";
    private static String TAG_JUDUL = "Judul";
    private static String TAG_CONTENTS = "Contents";
    private static String TAG_DATE_CREATED = "TanggalDiBuat";
    private static String TAG_DATE_START = "TanggalMulai";
    private static String TAG_DATE_END = "TanggalSelesai";

    public static ArrayList<HasanahPromo> getListOfHasanahPromo(String url) throws Exception {

        JSONArray arrayOfHasanahPromo = GlobalFunction.GetJSONArray(url);
        ArrayList<HasanahPromo> listOfHasanahPromo =  new ArrayList<HasanahPromo>();

        if (arrayOfHasanahPromo != null) {
            if (arrayOfHasanahPromo.length() > 0) {
                for (int j = 0; j < arrayOfHasanahPromo.length(); j++) {
                    JSONObject detailHasanahPromo = arrayOfHasanahPromo.getJSONObject(j);

                    HasanahPromo hasanahPromo = new HasanahPromo();
                    hasanahPromo.setID(detailHasanahPromo.getInt(TAG_ID));
                    hasanahPromo.setNama(detailHasanahPromo.getString(TAG_JUDUL));
                    hasanahPromo.setCOntents(detailHasanahPromo.getString(TAG_CONTENTS));
                    hasanahPromo.setDateCreated(detailHasanahPromo.getString(TAG_DATE_CREATED));
                    hasanahPromo.setDateStart(detailHasanahPromo.getString(TAG_DATE_START));
                    hasanahPromo.setDateEnd(detailHasanahPromo.getString(TAG_DATE_END));
                    listOfHasanahPromo.add(hasanahPromo);
                }
            }
        }

        return listOfHasanahPromo;
    }


}
