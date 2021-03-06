package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.HasanahPromo;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class HasanahPromoAdapter extends ArrayAdapter<HasanahPromo> {

    Context context;
    int resource;
    ArrayList<HasanahPromo> listOfHasanahPromo;

    public HasanahPromoAdapter(Context context, int resource, ArrayList<HasanahPromo> listOfHasanahPromo) {
        super(context, resource, listOfHasanahPromo);

        this.context = context;
        this.resource = resource;
        this.listOfHasanahPromo = listOfHasanahPromo;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        HasanahPromo hasanahPromo = listOfHasanahPromo.get(position);

        TextView textViewHasanahPromo = (TextView) view.findViewById(R.id.TextViewHasanahPromo);
        textViewHasanahPromo.setText(hasanahPromo.getNama());

        return view;
    }

}
