package com.yksayttyt.Cozuyorumm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.yksayttyt.Cozuyorumm.Cerceve.AnaSayfaFragment;
import com.yksayttyt.Cozuyorumm.Cerceve.AramaFragment;
import com.yksayttyt.Cozuyorumm.Cerceve.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AnaSayfaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Context mContext;
    Fragment seciliCerceve=null; // fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(secili);
        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new AnaSayfaFragment()).commit();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener secili=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if(menuItem.getItemId()==R.id.nav_home)
            {
                seciliCerceve=new AnaSayfaFragment();
                //Ana Sayfa
            }
            else if(menuItem.getItemId()==R.id.nav_arama)
            {
                seciliCerceve=new AramaFragment();
                //Arama Fragment
            }
            else if(menuItem.getItemId()==R.id.nav_ekle)
            {
                Intent goEkleme=new Intent(AnaSayfaActivity.this,EklemeActivity.class);
                startActivity(goEkleme);
            }
            else if(menuItem.getItemId()==R.id.nav_person)
            {
                SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                editor.putString("profilid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                System.out.println("Secili"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
                editor.apply();


                seciliCerceve=new ProfilFragment();

                //Profil Fragment
            }
            if(seciliCerceve!=null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,seciliCerceve).commit();
            }
            return true;
        }
    };
}
