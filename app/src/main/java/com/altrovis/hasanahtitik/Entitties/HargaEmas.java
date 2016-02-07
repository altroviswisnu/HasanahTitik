package com.altrovis.hasanahtitik.Entitties;

import java.util.Date;

/**
 * Created by Wisnu on 03/02/2016.
 */
public class HargaEmas {

    private int ID;
    private String Nama;
    private double HargaJual;
    private double HargaBeli;
    private Date Tanggal;

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

    public double getHargaJual() {
        return HargaJual;
    }

    public void setHargaJual(double hargaJual) {
        HargaJual = hargaJual;
    }

    public double getHargaBeli() {
        return HargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        HargaBeli = hargaBeli;
    }

    public Date getTanggal() {
        return Tanggal;
    }

    public void setTanggal(Date tanggal) {
        Tanggal = tanggal;
    }
}
