package com.styx.gta.homeui.adapter;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.model.ThermoStat;
import com.styx.gta.homeui.ui.view.FontTextView.FontTextView;

import java.util.List;

/**
 * Created by amal.george on 02-11-2016.
 */
public class ThermoStatAdapter extends RecyclerView.Adapter<ThermoStatAdapter.MyViewHolder> {

    private List<ThermoStat> moviesList;
    private DatabaseReference mDatabaseReference;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public FontTextView textViewDeviceName, textViewDeviceValue;
        public ImageButton buttonRemove;

        public MyViewHolder(View view) {
            super(view);
            textViewDeviceName = (FontTextView) view.findViewById(R.id.textViewDeviceName);
            textViewDeviceValue = (FontTextView) view.findViewById(R.id.textViewDeviceValue);
            buttonRemove = (ImageButton) view.findViewById(R.id.buttonRemove);
        }
    }


    public ThermoStatAdapter(List<ThermoStat> moviesList, DatabaseReference mDatabaseReference) {
        this.moviesList = moviesList;
        this.mDatabaseReference = mDatabaseReference;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_device, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ThermoStat movie = moviesList.get(position);
        holder.textViewDeviceName.setText(movie.getThermostatName());
        holder.textViewDeviceValue.setText(movie.getThermostatValue());
        holder.buttonRemove.setVisibility(View.VISIBLE);
        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.child(movie.getThermostatID()).setValue(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}