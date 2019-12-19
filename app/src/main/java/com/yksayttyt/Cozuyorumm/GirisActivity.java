package com.yksayttyt.Cozuyorumm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GirisActivity extends AppCompatActivity {

    EditText edt_kullaniciAdi,edt_Sifre;
    FirebaseAuth girisYetkisi;
    Button btnGiris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        edt_kullaniciAdi=findViewById(R.id.edt_kulaniciAdi);
        edt_Sifre=findViewById(R.id.edt_Sifre);
        girisYetkisi=FirebaseAuth.getInstance();
        btnGiris=findViewById(R.id.btn_giris2);
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eMail,sifre;
                eMail=edt_kullaniciAdi.getText().toString().trim();
                sifre=edt_Sifre.getText().toString();

                if(TextUtils.isEmpty(eMail)||TextUtils.isEmpty(sifre))
                {

                    Toast.makeText(GirisActivity.this,"Email veya Sifre Bos",Toast.LENGTH_LONG).show();

                }
                else
                {
                    girisYetkisi.signInWithEmailAndPassword(eMail,sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            final ProgressDialog pd=new ProgressDialog(GirisActivity.this);
                            pd.setMessage("Giriş Yapılıyor");
                            pd.show();


                            Intent intent=new Intent(GirisActivity.this,AnaSayfaActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                Toast.makeText(GirisActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void goKayit(View view)
    {
        startActivity(new Intent(GirisActivity.this,KayitOlActivity.class));
    }
}
