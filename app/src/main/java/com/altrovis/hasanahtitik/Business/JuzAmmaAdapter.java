package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.altrovis.hasanahtitik.Entitties.JuzAmma;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class JuzAmmaAdapter extends ArrayAdapter<JuzAmma> {

    Context context;
    int resource;
    ArrayList<JuzAmma> listOfJuzAmma;

    public JuzAmmaAdapter(Context context, int resource, ArrayList<JuzAmma> listJuzAmma) {
        super(context, resource, listJuzAmma);

        this.context = context;
        this.resource = resource;
        this.listOfJuzAmma = listJuzAmma;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        JuzAmma juzAmma = listOfJuzAmma.get(position);

        return view;
    }

}
