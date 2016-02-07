package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.LokasiATM;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class LokasiATMAdapter extends ArrayAdapter<LokasiATM> {

    Context context;
    int resource;
    ArrayList<LokasiATM> listOfATM;

    public LokasiATMAdapter(Context context, int resource, ArrayList<LokasiATM> listOfATM) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        this.listOfATM = listOfATM;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        LokasiATM atm = listOfATM.get(position);
        TextView textViewNamaATM = (TextView) view.findViewById(R.id.TextViewLokasiATM);
        textViewNamaATM.setText(atm.getNama());

        return view;
    }

}
