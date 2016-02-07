package com.altrovis.hasanahtitik.Entitties;

/**
 * Created by Wisnu on 02/02/2016.
 */
public class Kabupaten {

    private int ID;
    private String Nama;
    private int ProvinsiID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public int getProvinsiID() {
        return ProvinsiID;
    }

    public void setProvinsiID(int provinsiID) {
        ProvinsiID = provinsiID;
    }
}
