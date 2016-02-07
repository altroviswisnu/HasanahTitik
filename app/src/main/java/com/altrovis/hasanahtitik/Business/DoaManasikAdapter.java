package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.altrovis.hasanahtitik.Entitties.DoaManasik;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class DoaManasikAdapter extends ArrayAdapter<DoaManasik> {

    Context context;
    int resource;
    ArrayList<DoaManasik> listOfDoaManasik;

    public DoaManasikAdapter(Context context, int resource, ArrayList<DoaManasik> listOfDoaManasik) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        this.listOfDoaManasik = listOfDoaManasik;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        DoaManasik doaManasik = listOfDoaManasik.get(position);
        
        return view;
    }
}
