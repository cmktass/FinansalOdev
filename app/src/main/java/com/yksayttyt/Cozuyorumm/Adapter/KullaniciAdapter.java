package com.yksayttyt.Cozuyorumm.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yksayttyt.Cozuyorumm.Cerceve.ProfilFragment;
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

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.ViewHolder>
{
    private Context mContext;
    private List<Kullanici> mKullanicilar;     //Kulanıcılar Listesi
    private FirebaseUser firebaseUser;

    public KullaniciAdapter(Context mContext, List<Kullanici> mKullanicilar)//KURUCU
    {
        this.mContext = mContext;
        this.mKullanicilar = mKullanicilar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) //Kulanıcı Ogesi Baglama islemi
    {
        View view= LayoutInflater.from(mContext).inflate(R.layout.kullanici_ogesi,parent,false);
        return new KullaniciAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            final Kullanici kisi=mKullanicilar.get(position);
            holder.arkadaslik.setVisibility(View.VISIBLE);
            holder.kullaniciAdi.setText(kisi.getKullaniciAdi());
            holder.ad.setText(kisi.getIsim());
            Glide.with(mContext).load(kisi.getProfilResmi()).into(holder.profil_resmi);
            takipEdiliyor(kisi.getId(),holder.arkadaslik);
            if(kisi.getId().equals(firebaseUser.getUid()))
            {
                holder.arkadaslik.setVisibility(View.INVISIBLE);

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                    editor.putString("profilid",kisi.getId());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,
                            new ProfilFragment()).commit();
                }
            });
            holder.arkadaslik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.arkadaslik.getText().toString()=="TakipEt")
                    {
                        FirebaseDatabase.getInstance().getReference().child("Takip").child(firebaseUser.getUid())
                                .child("takipEdilenler").child(kisi.getId()).setValue(true);

                        FirebaseDatabase.getInstance().getReference().child("Takip").child(kisi.getId())
                                .child("takipciler").child(firebaseUser.getUid()).setValue(true);
                    }
                    else
                    {
                        FirebaseDatabase.getInstance().getReference().child("Takip").child(firebaseUser.getUid())
                                .child("takipEdilenler").child(kisi.getId()).removeValue();

                        FirebaseDatabase.getInstance().getReference().child("Takip").child(kisi.getId())
                                .child("takipciler").child(firebaseUser.getUid()).removeValue();
                    }
                }
            });


    }

    @Override
    public int getItemCount() {


        return mKullanicilar.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder //Kulanıcı Ögesinin icersindekiler tanımlanır.
    {
        public TextView kullaniciAdi;
        public TextView ad;
        public ImageView profil_resmi;
        public Button arkadaslik;
        public ViewHolder(@NonNull View itemView) //Ogeleri Bağlama İşlemi
        {
            super(itemView);
            kullaniciAdi=itemView.findViewById(R.id.txt_kullaniciadi_oge);
            ad=itemView.findViewById(R.id.txt_ad_oge);
            profil_resmi=itemView.findViewById(R.id.profil_resmi);
            arkadaslik=itemView.findViewById(R.id.btn_arkadaslık_oge);

        }
    }
    private void takipEdiliyor(final String kullaniciId, final Button button)
    {
        DatabaseReference takipYolu= FirebaseDatabase.getInstance().getReference().child("Takip")
                .child(firebaseUser.getUid()).child("takipEdilenler");
        takipYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(kullaniciId).exists())
                {
                    button.setText("TakipEdiliyor");
                }
                else
                {
                    button.setText("TakipEt");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
