package com.example.lift11.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lift11.DodajLift;
import com.example.lift11.Mjerenje;
import com.example.lift11.Model.Lift;
import com.example.lift11.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IspisOldAdapter extends RecyclerView.Adapter<IspisOldAdapter.MyViewHolder>  {
    private Context mContext;
    private List<Lift> lifts_old;
    private SharedPreferences prefs;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public IspisOldAdapter(Context context, List<Lift> uploads) {
        mContext = context;
        lifts_old = uploads;
    }

    @NotNull
    @Override
    public IspisOldAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_old, parent, false);
        return new IspisOldAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull IspisOldAdapter.MyViewHolder holder, int position) {
        final Lift listLift=lifts_old.get(position);
        prefs = Objects.requireNonNull(mContext).getSharedPreferences("shared_pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        if(listLift.getZg_ime()!=null){
            holder.zg_name.setText(listLift.getZg_ime());

        }
        if(listLift.getPod_ime()!=null){
            holder.pod_name.setText(listLift.getPod_ime());

        }
        holder.lift_name.setText(listLift.getIme());
        holder.itemView.setOnClickListener(v -> {
            myRef=database.getInstance().getReference("Liftovi");
            Map<String, Object> update = new HashMap<>();
            update.put("is_connected", true);
            myRef.child(listLift.getKey()).updateChildren(update);
            editor.putString("lift_id",listLift.getKey());
            editor.apply();
            Intent intent = new Intent(mContext, Mjerenje.class);
            intent.putExtra("id_lift",listLift.getKey());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return lifts_old.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView zg_name,pod_name,lift_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            zg_name = itemView.findViewById(R.id.nz_zg);
            pod_name=itemView.findViewById(R.id.podzg_old);
            lift_name=itemView.findViewById(R.id.lift_old);
        }
    }
}
