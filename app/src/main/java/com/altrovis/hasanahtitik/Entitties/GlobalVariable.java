package com.altrovis.hasanahtitik.Entitties;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Wisnu on 04/02/2016.
 */
public class GlobalVariable {

    public static String UrlWebView = "";
    public static String TitleActionBar = "";
    public static HasanahPromo SelectedPromo;
    public static LatLng SelectedCoordinate;

    public static String UrlDoaHarian = "http://poc.raufan.com/BNIMobileService/GetListOfDoa";
    public static String UrlDoaManasik = "http://poc.raufan.com/BNIMobileService/GetListOfDoaManasik";
    public static String UrlJuzAmma = "http://poc.raufan.com/BNIMobileService/GetListOfJuzAmma";

    public static String UrlLokasiRumahSakit = "http://poc.raufan.com/BNIMobileService/GetListOfRumahSakit";
    public static String UrlLokasiATM = "http://poc.raufan.com/BNIMobileService/GetListOfATM";
    public static String UrlPromo = "http://poc.raufan.com/BNIMobileService/GetListOfPromo";

    //public static String UrlKartuMigran = "http://poc.raufan.com/BNIMobileService/GetFiturKartu";
    //public static String UrlJadwalBus = "http://poc.raufan.com/BNIMobileService/GetJadwalBus";

    public static String UrlKartuMigran = "http://poc.raufan.com/fiturkartu/getFiturKartuMobile";
    public static String UrlJadwalBus = "http://poc.raufan.com/jadwalbus/getContentJadwalBus/1";

}
