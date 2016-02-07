package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.HargaEmas;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class HargaEmasAdapter extends ArrayAdapter<HargaEmas> {

    Context context;
    int resource;
    ArrayList<HargaEmas> listOfHargaEmas;

    public HargaEmasAdapter(Context context, int resource, ArrayList<HargaEmas> listOfHargaEmas) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        this.listOfHargaEmas = listOfHargaEmas;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        HargaEmas emas = listOfHargaEmas.get(position);
        TextView textViewJenisEmas = (TextView) view.findViewById(R.id.TextViewJenisEmas);
        TextView textViewHargaJual = (TextView) view.findViewById(R.id.TextViewHargaJual);
        TextView textViewHargaBeli = (TextView) view.findViewById(R.id.TextViewHargaBeli);

        textViewJenisEmas.setText(emas.getNama());
        textViewHargaJual.setText("" + emas.getHargaJual());
        textViewHargaBeli.setText("" + emas.getHargaBeli());

        return view;
    }
}
