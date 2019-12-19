package com.yksayttyt.Cozuyorumm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.yksayttyt.Cozuyorumm.Cerceve.AnaSayfaFragment;

public class Kategorilendir extends AppCompatActivity {

    Button sec;
    Spinner dersler,konular;
    String secilenDers,secilenKonu;
    final String [] derslerList={"Matematik1","Matematik2","Geometri","Fizik","Kimya","Biyoloji","Türkçe"};
    final String[] mat1Konular={"Temel Kavramlar","Sayı Basamakları","Rasyonel Sayılar","1. Dereceden Denklemler","Basit Eşitsizlikler",
            "Mutlak Değer","Üslü Sayılar","Köklü Sayılar","Çarpanlara Ayırma","Bölünebilme Kuralları","OBEB–OKEK","Oran – Orantı","Cebirsel Problemler",
            "Problemler","Kümeler","Kartezyen Çarpım","Fonksiyonlar","Polinomlar","İkinci Dereceden Denklemler","Sayma","Olasılık – Kombinasyon – Permütasyon","İstatistik"};
    final String[] mat2Konular={"Mantık","Çarpanlara Ayırma","Permütasyon","Kombinasyon","Binom","Olasılık","İstatistik","İkinci Dereceden Denklemler","Karmaşık Sayılar",
            "Parabol","Polinomlar","Toplam-Çarpım","Mantık","Modüler Aritmetik","Eşitsizlikler","Logaritma","Diziler","Seriler","Limit ve Süreklilik","Türev","İntegral"};
    final String[] geoKonular={"Doğruda ve Üçgende Açılar","Dik ve Özel Üçgenler","Dik Üçgende Trigonemetrik Bağıntılar","İkizkenar ve Eşkenar Üçgen","Üçgende Alanlar","Üçgende Açıortay Bağıntıları",
            "Üçgende Kenarortay Bağıntıları","Üçgende Eşlik ve Benzerlik","Üçgende Açı-Kenar Bağıntıları","Çokgenler","Dörtgenler","Yamuk","Paralelkenar","Eşkenar Dörtgen – Deltoid"
            ,"Dikdörtgen","Çemberde Açılar","Çemberde Uzunluk","Daire","Prizmalar","Piramitler","Küre","Koordinat Düzlemi ve Noktanın Analitiği","Vektörler-1","Doğrunun Analitiği"
            ,"Tekrar Eden, Dönen ve Yansıyan Şekiller","Uzay Geometri","Dönüşümlerle Geometri","Trigonometri","Çemberin Analitiği","Genel Konik Tanımı","Parabol","Elips","Hiperbol"};
    final String[] fizikKonular={"Fizik Bilimine Giriş","Madde ve Özellikleri","Hareket ve Kuvvet","İş, Güç ve Enerji I","Isı ve Sıcaklık","Elektrostatik","Elektrik ve Manyetizma"
            ,"Basınç","Kaldırma Kuvveti","Dalgalar","Optik","Kuvvet ve Hareket","Tork ve Denge","Kütle Merkezi","Atışlar","Basit Makineler","Düzgün Çembersel Hareket","Basit Harmonik Hareket"
            ,"Dalga Mekaniği","Atom Fiziğine Giriş ve Radyoaktive","Modern Fizik","Modern Fiziğin Teknolojideki Uygulamaları"};
    final String[] kimyaKonular={"Kimya Bilimi","Atom ve Yapısı","Periyodik Sistem","Kimyasal Türler Arası Etkileşimler","Kimyasal Hesaplamalar","Kimyanın Temel Kanunları","Asit, Baz ve Tuz"
            ,"Maddenin Halleri","Karışımlar","Endüstride ve Canlılarda Enerji","Kimya Her Yerde","Sıvı Çözeltiler","Kimya ve Enerji","Tepkimelerde Hız ve Denge","Kimya ve Elektrik",
            "Karbon Kimyasına Giriş","Organik Bileşikler","Hayatımızdaki Kimya"};
    final String[] bioKonular={"Canlıların Ortak Özellikleri","Canlıların Temel Bileşenleri","Hücre ve Organelleri","Hücre Zarından Madde Geçişi","Canlıların Sınıflandırılması","Mitoz ve Eşeysiz Üreme"
            ,"Mayoz ve Eşeyli Üreme","Kalıtım","Ekosistem Ekolojisi","Güncel Çevre Sorunlar","Canlılarda Enerji Dönüşümü","Solunum","İnsan Fizyolojisi","Endokrin Sistemi","Duyu Organları","Destek ve Hareket Sistemi"
            ,"Sindirim Sistemi","Sinir Sistemi","Dolaşım Sistemi","Genden Proteine","Hayatın Başlangıcı ve Evrim","Bitkisel Dokular","Bitki Biyolojisi","Kominite ve Popülasyon Ekolojisi"};
    final String[] turkceKonular={"Sözcükte Anlam","Cümlede Anlam","Paragraf","Deyim ve Atasözü","Paragrafta Anlatım Tekniknleri","Paragrafta Konu-Ana Düşünce",
            "Paragrafta Yapı","Paragrafta Yardımcı Düşünce","Ses Bilgisi","Yazım Kuralları","Noktalama İşaretleri","Sözcüğün Yapısı","Sözcük Türleri","Fiiller","Sözcük Grupları","Cümlenin Ögeleri",
            "Cümle Türleri","Anlatım Bozukluğu"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorilendir);
        dersler=findViewById(R.id.spinner);
        konular=findViewById(R.id.spinner2);
        sec=findViewById(R.id.button);
        konuDersSecimi();
        sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnaSayfaFragment.ders=secilenDers;
                AnaSayfaFragment.dersKonu=secilenKonu;
                Intent intent=new Intent(Kategorilendir.this,AnaSayfaActivity.class);
                startActivity(intent);
            }
        });

    }
    public void konuDersSecimi()
    {
        final ArrayAdapter<String> adapter=new ArrayAdapter<String >(this,R.layout.support_simple_spinner_dropdown_item,derslerList);
        dersler.setAdapter(adapter);
        ArrayAdapter<String>adapter2=new ArrayAdapter<String >(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,mat1Konular);
        konular.setAdapter(adapter2);
        dersler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals(derslerList[0]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,mat1Konular);
                    konular.setAdapter(adapter2);

                }
                else if(parent.getSelectedItem().toString().equals(derslerList[1]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,mat2Konular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[2]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,geoKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[3]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,fizikKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[4]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,kimyaKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[5]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,bioKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[6]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(Kategorilendir.this,R.layout.support_simple_spinner_dropdown_item,turkceKonular);
                    konular.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        konular.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secilenDers=dersler.getSelectedItem().toString();
                secilenKonu=konular.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
