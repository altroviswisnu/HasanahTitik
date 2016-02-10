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


public class HajiUmrohFragment extends Fragment {

    public HajiUmrohFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_haji_umroh, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.GridViewMenu);
        final ArrayList<ItemMenu> listOfMenu = new ArrayList<ItemMenu>();
        listOfMenu.add(new ItemMenu(1, "Doa Manasik", R.drawable.doamanasik));
        listOfMenu.add(new ItemMenu(2, "Rumah Sakit", R.drawable.rumahsakit));
        listOfMenu.add(new ItemMenu(3, "Kiblat", R.drawable.arahkiblat));
        listOfMenu.add(new ItemMenu(4, "Jadwal Sholat", R.drawable.jadwalsholat));
        listOfMenu.add(new ItemMenu(5, "Jadwal Bus", R.drawable.jadwalbus));
        GridViewAdapter adapter = new GridViewAdapter(getContext(), R.layout.gridview_item_menu, listOfMenu);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listOfMenu.get(position).getID() == 1) {
                    Intent intent = new Intent(getActivity(), DoaManasikListActivity.class);
                    startActivity(intent);
                } else if (listOfMenu.get(position).getID() == 2) {
                    Intent intent = new Intent(getActivity(), LokasiRumahSakitMapActivity.class);
                    startActivity(intent);
                } else if (listOfMenu.get(position).getID() == 3) {

                } else if (listOfMenu.get(position).getID() == 4) {
                    Intent intent = new Intent(getActivity(), JadwalSholatActivity.class);
                    startActivity(intent);
                } else if (listOfMenu.get(position).getID() == 5) {
                    Intent intent = new Intent(getActivity(), JadwalBusActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
