package com.altrovis.hasanahtitik.Business;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altrovis.hasanahtitik.Entitties.ItemMenu;
import com.altrovis.hasanahtitik.R;

import java.util.ArrayList;

/**
 * Created by Wisnu on 02/02/2016.
 */
public class ItemMenuAdapter extends ArrayAdapter<ItemMenu> {

    Context context;
    int resource;
    ArrayList<ItemMenu> listOfItemMenu;

    public ItemMenuAdapter(Context context, int resource, ArrayList<ItemMenu> listOfItemMenu) {
        super(context, resource, listOfItemMenu);

        this.context = context;
        this.resource = resource;
        this.listOfItemMenu = listOfItemMenu;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){

        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view =  inflater.inflate(resource, viewGroup,false);
        }

        ItemMenu itemMenu = listOfItemMenu.get(position);
        TextView textViewMenuItem = (TextView) view.findViewById(R.id.TextViewMenuItem);
        textViewMenuItem.setText(itemMenu.getNama());

        return view;
    }
}
