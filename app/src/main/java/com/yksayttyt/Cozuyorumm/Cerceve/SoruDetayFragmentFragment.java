package com.yksayttyt.Cozuyorumm.Cerceve;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yksayttyt.Cozuyorumm.Adapter.GonderiAdapter;
import com.yksayttyt.Cozuyorumm.Model.Gonderi;
import com.yksayttyt.Cozuyorumm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SoruDetayFragmentFragment extends Fragment {

    String id;
    private RecyclerView recyclerView;
    private GonderiAdapter gonderiAdapter;
    private List<Gonderi>gonderiler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_soru_detay,container,false);
        SharedPreferences preferences=getContext().getSharedPreferences("PREFS",Context.MODE_PRIVATE);
        id=preferences.getString("postid","");
        recyclerView=view.findViewById(R.id.detayRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        gonderiler=new ArrayList<>();
        gonderiAdapter=new GonderiAdapter(getContext(),gonderiler);
        recyclerView.setAdapter(gonderiAdapter);
        readPost();
        return  view;

    }

    private void readPost() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Gonderiler").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     gonderiler.clear();
                     Gonderi gonderi=dataSnapshot.getValue(Gonderi.class);
                     gonderiler.add(gonderi);
                     gonderiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
