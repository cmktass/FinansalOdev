package com.yksayttyt.Cozuyorumm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {





    FirebaseUser baslangicKullanici;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @Override
    protected void onStart() {

        super.onStart();
        baslangicKullanici= FirebaseAuth.getInstance().getCurrentUser();
        if(baslangicKullanici!=null)
        {
            startActivity(new Intent(MainActivity.this,AnaSayfaActivity.class));
            finish();
        }
    }

    public void girisYap(View view)
    {
            startActivity(new Intent(MainActivity.this,GirisActivity.class));
    }
    public void kayitOl(View view)
    {
        startActivity(new Intent(MainActivity.this,KayitOlActivity.class));
    }
}
