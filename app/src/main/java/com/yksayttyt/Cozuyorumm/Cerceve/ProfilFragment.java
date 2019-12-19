package com.yksayttyt.Cozuyorumm.Cerceve;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.yksayttyt.Cozuyorumm.Adapter.ProfilGonderiAdapter;
import com.yksayttyt.Cozuyorumm.Model.Gonderi;
import com.yksayttyt.Cozuyorumm.Model.Kullanici;
import com.yksayttyt.Cozuyorumm.ProfilAyarlarActivity;
import com.yksayttyt.Cozuyorumm.ProfilDetayActivity;
import com.yksayttyt.Cozuyorumm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    ImageView profilResmi,kaydedilenFotofraflar,kendiFotograflarim,kategori;
    TextView kullaniciAdi,sorular,takipciler,takip,profilismi;
    Button profilDuzenle;
    FirebaseUser mevcutKullanici;
    String id;
    RecyclerView recyclerView;
    ProfilGonderiAdapter profilGonderiAdapter;
    List<Gonderi>goderiler;
    List<Gonderi>gonderiler2;
    public static  String profilDers=null;
    public static String profilDersKonu=null;
    Context a;
    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profil, container, false);
        mevcutKullanici= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        id=sharedPreferences.getString("profilid","");
        kategori=view.findViewById(R.id.kategori2);
        profilResmi=view.findViewById(R.id.aaaa);
        kaydedilenFotofraflar=view.findViewById(R.id.kaydedilenFotograflar);
        kendiFotograflarim=view.findViewById(R.id.kendiFotograflarim);
        profilismi=view.findViewById(R.id.profilismi);
        kullaniciAdi=view.findViewById(R.id.kullaniciAdi);
        sorular=view.findViewById(R.id.sorular);
        takip=view.findViewById(R.id.takip);
        takipciler=view.findViewById(R.id.takipciler);
        profilDuzenle=view.findViewById(R.id.profilDuzenle);
        recyclerView=view.findViewById(R.id.gonderiLER);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayout);
        goderiler=new ArrayList<>();
        gonderiler2=new ArrayList<>();
        kullaniciBilgileri();
        takipcileriAl();
        gonderiSayısıAl();
        myFoto();
        if(profilDers==null&&profilDersKonu==null)
        {
            profilGonderiAdapter=new ProfilGonderiAdapter(getContext(),goderiler);
        }
        else
        {
            profilGonderiAdapter=new ProfilGonderiAdapter(getContext(),gonderiler2);
        }

        recyclerView.setAdapter(profilGonderiAdapter);






        if(id.equals(mevcutKullanici.getUid()))
        {
            profilDuzenle.setText("Profili Düzenle");
        }
        else
        {
            takipKontrol();
            kaydedilenFotofraflar.setVisibility(View.GONE);
        }
        profilDuzenle.setOnClickListener(new View.OnClickListener() {//Profili Duzenleye basıldıgında
            @Override
            public void onClick(View v) {
                String btn=profilDuzenle.getText().toString();
                if(btn.equals("Profili Düzenle"))
                {
                    startActivity(new Intent(getContext(),ProfilDetayActivity.class));
                }
                else if(btn.equals("takip et"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Takip").child(mevcutKullanici.getUid()).child("takipEdilenler").child(id).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Takip").child(id).child("takipciler").child(mevcutKullanici.getUid()).setValue(true);
                }
                else if(btn.equals("takip ediliyor"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Takip").child(mevcutKullanici.getUid()).child("takipEdilenler").child(id).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Takip").child(id).child("takipciler").child(mevcutKullanici.getUid()).removeValue();
                }

            }
        });
        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfilAyarlarActivity.class));

            }
        });
        return  view;
    }
    private void kullaniciBilgileri()
    {
        DatabaseReference kullaniciYolu=FirebaseDatabase.getInstance().getReference("Kullanicilar").child(id);
        kullaniciYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(getContext()==null)
                {

                }

                    Kullanici kullanici=dataSnapshot.getValue(Kullanici.class);
                    kullaniciAdi.setText(kullanici.getKullaniciAdi());
                    profilismi.setText(kullanici.getIsim());
                    System.out.println(kullanici.getProfilResmi());
                   Glide.with(getContext()).load(kullanici.getProfilResmi()).into(profilResmi);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void takipKontrol()
    {
        DatabaseReference takipYolu=FirebaseDatabase.getInstance().getReference().child("Takip").child(mevcutKullanici.getUid()).child("takipEdilenler");

        takipYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(id).exists())
                {
                    profilDuzenle.setText("takip ediliyor");

                }
                else
                {
                    profilDuzenle.setText("takip et");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void takipcileriAl()
    {
        DatabaseReference yol=FirebaseDatabase.getInstance().getReference().child("Takip").child(id).child("takipciler");

        yol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                takipciler.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //takip edilen sayısı
        DatabaseReference yol2=FirebaseDatabase.getInstance().getReference().child("Takip").child(id).child("takipEdilenler");

        yol2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                takip.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void gonderiSayısıAl()
    {
        DatabaseReference gonderiSayisiYolu=FirebaseDatabase.getInstance().getReference("Gonderiler");

        gonderiSayisiYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Gonderi gonderi=snapshot.getValue(Gonderi.class);
                    if(gonderi.getGonderenId().equals(id))
                    {
                        i++;
                    }
                }
                sorular.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void myFoto()
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Gonderiler");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                goderiler.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Gonderi gonderi=snapshot.getValue(Gonderi.class);
                    if(gonderi.getGonderenId().equals(id))
                    {
                        goderiler.add(gonderi);

                    }



                    Collections.reverse(goderiler);
                    Collections.reverse(gonderiler2);
                    profilGonderiAdapter.notifyDataSetChanged();
                }
                for(int i=0;i<goderiler.size();i++)
                {
                    System.out.println(goderiler.size());
                    if(goderiler.get(i).getGonderiDersi().equals(profilDers)&&goderiler.get(i).getDersKonusu().equals(profilDersKonu))
                    {
                        gonderiler2.add(goderiler.get(i));

                    }
                }
                profilGonderiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
