package com.yksayttyt.Cozuyorumm.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yksayttyt.Cozuyorumm.Cerceve.SoruDetayFragmentFragment;
import com.yksayttyt.Cozuyorumm.Model.Gonderi;
import com.yksayttyt.Cozuyorumm.Model.Kullanici;
import com.yksayttyt.Cozuyorumm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GonderiAdapter extends RecyclerView.Adapter <GonderiAdapter.ViewHolder>{


    public Context mContext;
    public List<Gonderi>mGonderi;
    private FirebaseUser mevcutFirebaseUser;

    public GonderiAdapter(Context mContext, List<Gonderi> mGonderi) {
        this.mContext = mContext;
        this.mGonderi = mGonderi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)//Layoutumuzu bağlama işlemini yapıyoruz.
    {

        View view= LayoutInflater.from(mContext).inflate(R.layout.anasayfa_gonderi_ogesi,parent,false);
        return new GonderiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) //İçeriğimizde ne olacaksa position olarak alır sayfaya yansıtır.
    {
            mevcutFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            final Gonderi gonderi=mGonderi.get(position);
        Glide.with(mContext).load(gonderi.getGonderiResmi()).into(holder.gonderiResmiOgesi);

        if(gonderi.getGonderiHakında().equals(""))
        {
            holder.txtGonderiHakkında.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.txtGonderiHakkında.setVisibility(View.VISIBLE);
            holder.txtGonderiHakkında.setText(gonderi.getGonderiHakında());
        }
        holder.txtDersAdi.setText(gonderi.getGonderiDersi());

        holder.txtDersKonusuAdi.setText(gonderi.getDersKonusu());

        gonderenBilgileri(holder.kullaniciResmiOgesi,holder.txtKullaniciAdi,gonderi.getGonderenId());
       kaydet(gonderi.getGonderiId(),holder.kaydetResmiOgesi);

        kaydet(gonderi.getGonderiId(),holder.kaydetResmiOgesi);
        holder.gonderiResmiOgesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("postid",gonderi.getGonderiId());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new SoruDetayFragmentFragment()).addToBackStack("AnaSayfaFragment").commit();
            }
        });
        holder.kaydetResmiOgesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.kaydetResmiOgesi.getTag().equals("kaydet"))
                {
                    DatabaseReference databasekayitEt=FirebaseDatabase.getInstance().getReference().child("Kaydedilenler").child(gonderi.getGonderiId());
                    databasekayitEt.child(mevcutFirebaseUser.getUid()).setValue(true);


                }
                else if(holder.kaydetResmiOgesi.getTag().equals("kaydedildi"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Kaydedilenler").child(gonderi.getGonderiId()).removeValue();

                }
            }
        });


    }

    @Override
    public int getItemCount() //Recycler kaç eleman gösterecek.
    {
        return mGonderi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder //Recyler View'in 1 satırının nasıl oluştulacağını tutugumuz layout içersindeki elemanlar tanımlanır
    {

        public ImageView kullaniciResmiOgesi,cevaplaResmiOgesi,kaydetResmiOgesi,gonderiResmiOgesi;
        public TextView txtKullaniciAdi,txtDersAdi,txtDersKonusuAdi,txtGonderiHakkında;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kullaniciResmiOgesi=itemView.findViewById(R.id.kullanici_resmi_Ogesi);
            cevaplaResmiOgesi=itemView.findViewById(R.id.cevaplaOgesi);
            kaydetResmiOgesi=itemView.findViewById(R.id.kaydetOgesi);
            gonderiResmiOgesi=itemView.findViewById(R.id.gonderiResmi);
            txtKullaniciAdi=itemView.findViewById(R.id.txt_KulaniciAdi);
            txtDersAdi=itemView.findViewById(R.id.dersAdi);
            txtDersKonusuAdi=itemView.findViewById(R.id.dersKonuAdi);
            txtGonderiHakkında=itemView.findViewById(R.id.GonderiHakkinda);


        }
    }
    private void kaydet(String gonderiId, final ImageView imageView)
    {
        final FirebaseUser mevcutK=FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databasekayitEt=FirebaseDatabase.getInstance().getReference().child("Kaydedilenler").child(gonderiId);
        databasekayitEt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(mevcutK.getUid()).exists())
                {
                    imageView.setImageResource(R.drawable.ic_kaydedildi);
                    imageView.setTag("kaydedildi");
                }
                else
                {
                    imageView.setImageResource(R.drawable.ic_kaydet);
                    imageView.setTag("kaydet");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void gonderenBilgileri(final ImageView profilResmi, final TextView kullaniciAdi, String kullaniciId)
    {
        DatabaseReference veriYolu= FirebaseDatabase.getInstance().getReference("Kullanicilar").child(kullaniciId);
        veriYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Kullanici kullanici=dataSnapshot.getValue(Kullanici.class);
                kullaniciAdi.setText(kullanici.getKullaniciAdi());
                Glide.with(mContext).load(kullanici.getProfilResmi()).into(profilResmi);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
