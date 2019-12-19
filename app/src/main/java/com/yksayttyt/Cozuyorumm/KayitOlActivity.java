package com.yksayttyt.Cozuyorumm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yksayttyt.Cozuyorumm.Model.Kullanici;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KayitOlActivity extends AppCompatActivity {

    EditText edt_kulaniciAdi,edt_Adi,edt_Email,edt_Sifre;

    FirebaseAuth yetki;       //giris
    DatabaseReference yol;    //database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        edt_kulaniciAdi=findViewById(R.id.edt_kulaniciAdi);
        edt_Adi=findViewById(R.id.edt_Ad);
        edt_Email=findViewById(R.id.edt_Email);
        edt_Sifre=findViewById(R.id.edt_Sifre);
        yetki=FirebaseAuth.getInstance();  //referans
    }
    public void kayitOl(View view)
    {
        final Kullanici kisi=new Kullanici();
        kisi.setEmail(edt_Email.getText().toString());
        kisi.setSifre(edt_Sifre.getText().toString());
        kisi.setIsim(edt_Adi.getText().toString());
        kisi.setKullaniciAdi(edt_kulaniciAdi.getText().toString());
        yetki.createUserWithEmailAndPassword(kisi.getEmail(),kisi.getSifre()).addOnCompleteListener(KayitOlActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {


                                        FirebaseUser firebaseUser=yetki.getCurrentUser();
                                        String kullaniciId=firebaseUser.getUid();
                                        yol= FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
                                        HashMap<String,Object>hashMap=new HashMap<>();
                                        hashMap.put("id",kullaniciId);
                                        hashMap.put("kullaniciAdi",kisi.getKullaniciAdi());
                                        hashMap.put("isim",kisi.getIsim());
                                        hashMap.put("bio","");
                                        hashMap.put("email",kisi.getEmail());


                                        yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    startActivity(new Intent(KayitOlActivity.this,GirisActivity.class));
                                                }
                                            }
                                        });

                                    }



                            else
                            {
                                Toast.makeText(KayitOlActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }






        });

    }
    public void goGiris(View view)
    {
        startActivity(new Intent(KayitOlActivity.this,GirisActivity.class));
    }


}
