package com.yksayttyt.Cozuyorumm.Model;

import com.google.firebase.Timestamp;

public class Gonderi
{
    private String gonderiId;
    private String gonderenId;
    private String gonderiDersi;
    private String dersKonusu;
    private String gonderiHakında;
    private String gonderiResmi;
    private String tarih;
    private Timestamp date;
    public String getGonderiResmi() {
        return gonderiResmi;
    }

    public void setGonderiResmi(String gonderiResmi) {
        this.gonderiResmi = gonderiResmi;
    }

    public Gonderi()
    {

    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public Gonderi(String gonderiId, String gonderenId, String gonderiDersi, String dersKonusu, String gonderiHakında, String gonderiResmi, String tarih) {
        this.gonderiId = gonderiId;
        this.gonderenId = gonderenId;
        this.gonderiDersi = gonderiDersi;
        this.dersKonusu = dersKonusu;
        this.gonderiHakında = gonderiHakında;
        this.gonderiResmi=gonderiResmi;
        this.tarih=tarih;
    }

    public String getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(String gonderiId) {
        this.gonderiId = gonderiId;
    }

    public String getGonderenId() {
        return gonderenId;
    }

    public void setGonderenId(String gonderenId) {
        this.gonderenId = gonderenId;
    }

    public String getGonderiDersi() {
        return gonderiDersi;
    }

    public void setGonderiDersi(String gonderiDersi) {
        this.gonderiDersi = gonderiDersi;
    }

    public String getDersKonusu() {
        return dersKonusu;
    }

    public void setDersKonusu(String dersKonusu) {
        this.dersKonusu = dersKonusu;
    }

    public String getGonderiHakında() {
        return gonderiHakında;
    }

    public void setGonderiHakında(String gonderiHakında) {
        this.gonderiHakında = gonderiHakında;
    }
}
