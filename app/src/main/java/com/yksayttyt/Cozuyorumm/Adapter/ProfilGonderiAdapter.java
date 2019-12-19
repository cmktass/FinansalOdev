package com.yksayttyt.Cozuyorumm.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yksayttyt.Cozuyorumm.Cerceve.SoruDetayFragmentFragment;
import com.yksayttyt.Cozuyorumm.Model.Gonderi;
import com.yksayttyt.Cozuyorumm.R;

import java.util.List;

public class ProfilGonderiAdapter extends RecyclerView.Adapter<ProfilGonderiAdapter.ViewHolder>
{
    private Context mContext;
    private List<Gonderi> mGonderiler;

    public ProfilGonderiAdapter(Context context, List<Gonderi> goderiler)
    {
        this.mContext=context;
        this.mGonderiler=goderiler;
    }
    public  ProfilGonderiAdapter()
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.profil_gonderiler,parent,false);

        return new ProfilGonderiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Gonderi gonderi=mGonderiler.get(position);
        Glide.with(mContext).load(gonderi.getGonderiResmi()).into(holder.gonderiSorusu);
        holder.gonderiSorusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("postid",gonderi.getGonderiId());
                editor.apply();
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new SoruDetayFragmentFragment()).addToBackStack("ProfilFragment").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGonderiler.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView gonderiSorusu;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            gonderiSorusu=itemView.findViewById(R.id.gonderiler);
        }
    }
}
