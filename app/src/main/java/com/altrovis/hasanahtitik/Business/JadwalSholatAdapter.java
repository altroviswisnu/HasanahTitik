package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.JadwalSholat;
import com.altrovis.hasanahtitik.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Wisnu on 02/02/2016.
 */
public class JadwalSholatAdapter extends ArrayAdapter<JadwalSholat> {

    Context context;
    int resource;
    ArrayList<JadwalSholat> listOfJadwalSholat;
    DateFormat dateFormat;

    public JadwalSholatAdapter(Context context, int resource, ArrayList<JadwalSholat> listJadwalSholat) {
        super(context, resource, listJadwalSholat);

        this.context = context;
        this.resource = resource;
        this.listOfJadwalSholat = listJadwalSholat;

        dateFormat = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){

        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view =  inflater.inflate(resource, viewGroup,false);
        }

        TextView textViewNamaSholat = (TextView) view.findViewById(R.id.TextViewNamaSholat);
        TextView textViewJamSholat = (TextView) view.findViewById(R.id.TextViewJamSholat);

        JadwalSholat jadwalSholat = listOfJadwalSholat.get(position);

        if(jadwalSholat.getJenisSholatID() == 1){
            textViewNamaSholat.setText("Subuh");
        } else if(jadwalSholat.getJenisSholatID() == 2){
            textViewNamaSholat.setText("Zuhur");
        } else if(jadwalSholat.getJenisSholatID() == 3){
            textViewNamaSholat.setText("Ashar");
        } else if(jadwalSholat.getJenisSholatID() == 4){
            textViewNamaSholat.setText("Maghrib");
        } else if(jadwalSholat.getJenisSholatID() == 5){
            textViewNamaSholat.setText("Isya");
        }

        String dateFormatted = dateFormat.format(jadwalSholat.getTanggalMulai());
        textViewJamSholat.setText(dateFormatted);

        return view;
    }
}
