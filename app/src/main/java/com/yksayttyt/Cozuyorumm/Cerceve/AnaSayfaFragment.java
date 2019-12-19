package com.yksayttyt.Cozuyorumm.Cerceve;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yksayttyt.Cozuyorumm.Adapter.GonderiAdapter;
import com.yksayttyt.Cozuyorumm.Kategorilendir;
import com.yksayttyt.Cozuyorumm.Model.Gonderi;
import com.yksayttyt.Cozuyorumm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnaSayfaFragment extends Fragment {
    private RecyclerView recyclerView;
    private GonderiAdapter gonderiAdapter;
    private List<Gonderi>gonderiList;
    private List<Gonderi>gonderiList2;
    private  List<String> takipListesi;
    private FirebaseAuth firebaseAuth;
    ImageView kategorilendir;
    public static String ders;
    public static String dersKonu;
    public AnaSayfaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println(ders+dersKonu);
        firebaseAuth=FirebaseAuth.getInstance();
        View view= inflater.inflate(R.layout.fragment_ana_sayfa, container, false);
        kategorilendir=view.findViewById(R.id.kategorilendir);
        recyclerView=view.findViewById(R.id.recyclerViewAnaSayfa);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayout);
        takipKontrolu();

        kategorilendir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Kategorilendir.class));
            }
        });
        gonderiList=new ArrayList<>();
        gonderiList2=new ArrayList<>();

        if(ders==null&&dersKonu==null)
        {
            gonderiAdapter=new GonderiAdapter(getContext(),gonderiList);

        }
        else
        {

            gonderiAdapter=new GonderiAdapter(getContext(),gonderiList2);
        }

        recyclerView.setAdapter(gonderiAdapter);
        kategorilendir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Kategorilendir.class));
            }
        });
        return view;

    }
    private void takipKontrolu()
    {
        takipListesi=new ArrayList<>();
        DatabaseReference takipYolu=FirebaseDatabase.getInstance().getReference("Takip").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("takipEdilenler");
        takipYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                takipListesi.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    takipListesi.add(snapshot.getKey());

                }
                takipListesi.add(firebaseAuth.getUid());
                gonderileriOkul();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void gonderileriOkul()
    {
        DatabaseReference gonderiYolu= FirebaseDatabase.getInstance().getReference("Gonderiler");
        gonderiYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gonderiList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Gonderi gonderi=snapshot.getValue(Gonderi.class);
                    for(String id:takipListesi)
                    {
                        if(gonderi.getGonderenId().equals(id))
                        {
                            gonderiList.add(gonderi);
                        }
                    }
                }
                for(int i=0;i<gonderiList.size();i++)
                {
                    if(gonderiList.get(i).getGonderiDersi().equals(ders)&&gonderiList.get(i).getDersKonusu().equals(dersKonu))
                    {
                            gonderiList2.add(gonderiList.get(i));
                    }
                }
                gonderiAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
