package com.yksayttyt.Cozuyorumm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class EklemeActivity extends AppCompatActivity {

    Bitmap bitmap;
    Uri resimUri;
    String myUri="";
    ImageView imageKapat,resim;
    TextView txt_Gonder;
    EditText edt_SoruHakkında;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference yol;
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
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekleme);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        imageKapat=findViewById(R.id.ekraniKapatImageView);
        resim=findViewById(R.id.eklenen_resim);
        txt_Gonder=findViewById(R.id.txt_ekle);
        edt_SoruHakkında=findViewById(R.id.soruHakkında);
        dersler=findViewById(R.id.derslerSecim);
        konular=findViewById(R.id.konularSecim);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        konuDersSecimi();

        imageKapat.setOnClickListener(new View.OnClickListener() { //Geriye bastıgında anasayfaya gider.
            @Override
            public void onClick(View v) {
                Intent goAnaSayfa=new Intent(EklemeActivity.this,AnaSayfaActivity.class);
                startActivity(goAnaSayfa);
                finish();
            }
        });

        txt_Gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd=new ProgressDialog(EklemeActivity.this);
                pd.setMessage("Yükleniyor");
                pd.show();
                resimYukle();
                
            }
        });

    }

    public void resimSec(View view) //Resmi galariden seçiyor.
    {
            if(ContextCompat.checkSelfPermission(EklemeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

            }
            else
            {
                Intent goGaleri=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(goGaleri,2);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent goGaleri=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(goGaleri,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==2 && resultCode==RESULT_OK && data!=null)
        {
            resimUri=data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28)
                {
                    ImageDecoder.Source source=ImageDecoder.createSource(this.getContentResolver(),resimUri);
                    bitmap=ImageDecoder.decodeBitmap(source);
                    resim.setImageBitmap(bitmap);

                }
                else
                {
                    bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),resimUri);
                    resim.setImageBitmap(bitmap);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    } //Resmi bitmape çeviriyor.

    public void resimYukle()
    {
        if(resimUri!=null)
        {
            final UUID uuıd=UUID.randomUUID();
            final String resimAdi="Resimler/"+uuıd;
            storageReference.child(resimAdi).putFile(resimUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference yeniReferans=FirebaseStorage.getInstance().getReference(resimAdi);
                    yeniReferans.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            yol= FirebaseDatabase.getInstance().getReference().child("Gonderiler").child(uuıd.toString());
                            HashMap<String,Object>hashMap=new HashMap<>();
                            hashMap.put("gonderiId",uuıd.toString());
                            hashMap.put("gonderenId",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            hashMap.put("gonderiHakında",edt_SoruHakkında.getText().toString());
                            hashMap.put("gonderiDersi",secilenDers);
                            hashMap.put("gonderiResmi",uri.toString());
                            hashMap.put("dersKonusu",secilenKonu);
                          // hashMap.put("date", FieldValue.serverTimestamp());
                            System.out.println(FieldValue.serverTimestamp());
                            yol.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent=new Intent(EklemeActivity.this,AnaSayfaActivity.class);
                                    startActivity(intent);

                                }
                            });

                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
    public void konuDersSecimi()
    {
        final ArrayAdapter<String> adapter=new ArrayAdapter<String >(this,R.layout.support_simple_spinner_dropdown_item,derslerList);
        dersler.setAdapter(adapter);
        ArrayAdapter<String>adapter2=new ArrayAdapter<String >(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,mat1Konular);
        konular.setAdapter(adapter2);
        dersler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals(derslerList[0]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,mat1Konular);
                    konular.setAdapter(adapter2);

                }
                else if(parent.getSelectedItem().toString().equals(derslerList[1]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,mat2Konular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[2]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,geoKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[3]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,fizikKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[4]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,kimyaKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[5]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,bioKonular);
                    konular.setAdapter(adapter2);
                }
                else if(parent.getSelectedItem().toString().equals(derslerList[6]))
                {
                    ArrayAdapter<String>adapter2=new ArrayAdapter<String>(EklemeActivity.this,R.layout.support_simple_spinner_dropdown_item,turkceKonular);
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
