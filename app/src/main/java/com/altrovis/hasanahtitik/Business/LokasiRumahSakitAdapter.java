package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.LokasiRumahSakit;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class LokasiRumahSakitAdapter extends ArrayAdapter<LokasiRumahSakit> {

    Context context;
    int resource;
    ArrayList<LokasiRumahSakit> listOfRumahSakit;

    public LokasiRumahSakitAdapter(Context context, int resource, ArrayList<LokasiRumahSakit> listOfRumahSakit) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        this.listOfRumahSakit = listOfRumahSakit;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        LokasiRumahSakit rumahSakit = listOfRumahSakit.get(position);
        TextView textViewNamaRumahSakit = (TextView) view.findViewById(R.id.TextViewRumahSakit);
        textViewNamaRumahSakit.setText(rumahSakit.getNama());

        return view;
    }

}
