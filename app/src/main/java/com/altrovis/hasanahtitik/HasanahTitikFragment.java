package com.altrovis.hasanahtitik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.altrovis.hasanahtitik.Business.GridViewAdapter;
import com.altrovis.hasanahtitik.Entitties.ItemMenu;

import java.util.ArrayList;

public class HasanahTitikFragment extends Fragment {

    public HasanahTitikFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hasanah_titik, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.GridViewMenu);
        final ArrayList<ItemMenu> listOfMenu = new ArrayList<ItemMenu>();
        listOfMenu.add(new ItemMenu(1, "Promo", R.drawable.promo));
        listOfMenu.add(new ItemMenu(2, "Kalkulator", R.drawable.kalkulator));
        listOfMenu.add(new ItemMenu(3, "Harga Emas", R.drawable.hargaemas));
        listOfMenu.add(new ItemMenu(4, "Lokasi ATM", R.drawable.atm));
        listOfMenu.add(new ItemMenu(5, "Juz Amma", R.drawable.juzamma));
        listOfMenu.add(new ItemMenu(6, "Jadwal Sholat", R.drawable.jadwalsholat));
        GridViewAdapter adapter = new GridViewAdapter(getContext(), R.layout.gridview_item_menu, listOfMenu);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listOfMenu.get(position).getID() == 1) {
                    Intent intent = new Intent(getActivity(), HasanahPromoListActivity.class);
                    startActivity(intent);
                } else if (listOfMenu.get(position).getID() == 2){

                } else if (listOfMenu.get(position).getID() == 3){

                } else if (listOfMenu.get(position).getID() == 4){
                    Intent intent = new Intent(getActivity(), LokasiATMMapActivity.class);
                    startActivity(intent);
                } else if (listOfMenu.get(position).getID() == 5){
                    Intent intent = new Intent(getActivity(), JuzAmmaListActivity.class);
                    startActivity(intent);
                } else if (listOfMenu.get(position).getID() == 6){
                    Intent intent = new Intent(getActivity(), JadwalSholatActivity.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }

}
