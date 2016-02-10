package com.altrovis.hasanahtitik.Entitties;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class HasanahPromo {

    private int ID;
    private String Nama;
    private String DateCreated;
    private String DateStart;
    private String DateEnd;
    private String UrlWebView;

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

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getUrlWebView() {
        return UrlWebView;
    }

    public void setUrlWebView(String urlWebView) {
        UrlWebView = urlWebView;
    }
}
