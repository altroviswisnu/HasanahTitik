package com.altrovis.hasanahtitik.Business;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wisnu on 09/02/2016.
 */
public class GlobalFunction {

    public static JSONArray GetJSONArray(String urlString) throws Exception {

        URL obj = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();

        try {
            if (responseCode == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                return jsonArray;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }

}
