package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.DoaHarian;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class DoaHarianAdapter extends ArrayAdapter<DoaHarian> {

    Context context;
    int resource;
    ArrayList<DoaHarian> listOfDoaHarian;

    public DoaHarianAdapter(Context context, int resource, ArrayList<DoaHarian> listOfDoaHarian) {
        super(context, resource,listOfDoaHarian);

        this.context = context;
        this.resource = resource;
        this.listOfDoaHarian = listOfDoaHarian;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        DoaHarian doaHarian = listOfDoaHarian.get(position);

        TextView textViewDoaHarian = (TextView) view.findViewById(R.id.TextViewDoaHarian);
        textViewDoaHarian.setText(doaHarian.getNama());

        return view;
    }

}
