package com.yksayttyt.Cozuyorumm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilAyarlarActivity extends AppCompatActivity {

    TextView profilAyarlar,profilCikis,dersKonuSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ayarlar);
        profilAyarlar=findViewById(R.id.porfilAyarlar);
        profilCikis=findViewById(R.id.porfilCikis);
        dersKonuSec=findViewById(R.id.porfilKategori);

        profilCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd=new ProgressDialog(ProfilAyarlarActivity.this);
                pd.setMessage("Yükleniyor");
                pd.show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfilAyarlarActivity.this,GirisActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();

            }
        });
        dersKonuSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        startActivity(new Intent(ProfilAyarlarActivity.this,ProfilKategorilendirActivity.class));
                finish();
            }
        });
        profilAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilAyarlarActivity.this,ProfilDetayActivity.class));
                finish();
            }
        });

    }
}
