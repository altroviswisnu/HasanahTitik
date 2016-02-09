package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.JuzAmma;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class JuzAmmaAdapter extends ArrayAdapter<JuzAmma> {

    Context context;
    int resource;
    ArrayList<JuzAmma> listOfJuzAmma;

    public JuzAmmaAdapter(Context context, int resource, ArrayList<JuzAmma> listOfJuzAmma) {
        super(context, resource, listOfJuzAmma);

        this.context = context;
        this.resource = resource;
        this.listOfJuzAmma = listOfJuzAmma;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        JuzAmma juzAmma = listOfJuzAmma.get(position);

        TextView textViewJuzAmma = (TextView) view.findViewById(R.id.TextViewJuzAmma);
        textViewJuzAmma.setText(juzAmma.getNama());

        return view;
    }

}
