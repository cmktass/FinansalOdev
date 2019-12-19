package com.yksayttyt.Cozuyorumm.Model;

public class Kullanici
{
    private String email;
    private String kullaniciAdi;
    private String isim;
    private String id;
    private String resim;
    private String bio;
    private String Sifre;

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String sifre) {
        Sifre = sifre;
    }

    public String getProfilResmi() {
        return resim;
    }

    public void setProfilResmi(String profilResmi) {
        this.resim = profilResmi;
    }

    public Kullanici(String email, String kullaniciAdi, String isim, String id, String profilResmi,String bio) {
        this.email = email;
        this.kullaniciAdi = kullaniciAdi;
        this.bio=bio;
        this.isim = isim;
        this.id = id;
        this.resim=profilResmi;
    }
    public Kullanici()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }


    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
