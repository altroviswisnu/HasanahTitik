package com.altrovis.hasanahtitik.Entitties;

import java.util.Date;

/**
 * Created by Wisnu on 02/02/2016.
 */
public class JadwalSholat {

    private int ID;
    private int JenisSholatID;
    private Date TanggalMulai;
    private int NegaraID;
    private int ProviniID;
    private int KabupatenID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getJenisSholatID() {
        return JenisSholatID;
    }

    public void setJenisSholatID(int jenisSholatID) {
        JenisSholatID = jenisSholatID;
    }

    public Date getTanggalMulai() {
        return TanggalMulai;
    }

    public void setTanggalMulai(Date tanggalMulai) {
        TanggalMulai = tanggalMulai;
    }

    public int getNegaraID() {
        return NegaraID;
    }

    public void setNegaraID(int negaraID) {
        NegaraID = negaraID;
    }

    public int getProviniID() {
        return ProviniID;
    }

    public void setProviniID(int proviniID) {
        ProviniID = proviniID;
    }

    public int getKabupatenID() {
        return KabupatenID;
    }

    public void setKabupatenID(int kabupatenID) {
        KabupatenID = kabupatenID;
    }
}
