package com.styx.gta.homeui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.adapter.ThermoStatAdapter;
import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.model.device.ThermoStat;

import java.util.ArrayList;
import java.util.List;

import static com.styx.gta.homeui.util.Constants.DEVICE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 02-11-2016.
 */

public class AddDeviceActivity extends BaseAppCompatActivity {

    private List<ThermoStat> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ThermoStatAdapter mAdapter;
    String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DEBUG = true;
        setContentView(R.layout.activity_add_device);
        recyclerView = (RecyclerView) findViewById(R.id.list_device);
        mAdapter = new ThermoStatAdapter(movieList, getmDatabase().child("user").child(getUid()).child("device"));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();
/*
        View.OnClickListener mClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonAdd:
                        DatabaseReference mChild=getmDatabase().child("user").child(getUid()).child("device").push();
                        ThermoStat mThermoStat=new ThermoStat(((EditText)findViewById(R.id.editTextDeviceName)).getText().toString(),(((EditText)findViewById(R.id.editTextDeviceValue)).getText().toString()));
                        mThermoStat.setThermostatID(mChild.getKey());
                        mChild.setValue(mThermoStat);
                        break;
                }
            }
        };
        */

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(AddDeviceActivity.this);
                View promptView = layoutInflater.inflate(R.layout.dialog_add_device, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddDeviceActivity.this);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setTitle("Add New Device");
                final EditText editText = (EditText) promptView.findViewById(R.id.editTextDeviceName);
                // setup a dialog window
                alertDialogBuilder.setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference mNewDevice=getmDatabase().child(USER).child(getUid()).child(DEVICE).push();
                                ThermoStat mThermoStat=new ThermoStat(editText.getText().toString(),0+"");
                                mThermoStat.setThermostatID(mNewDevice.getKey());
                                mNewDevice.setValue(mThermoStat);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(AddDeviceActivity.this, "Cancelled adding new device", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });
    }

    private void prepareMovieData() {
        getmDatabase().child("user").child(getUid()).child("device").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movieList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        ThermoStat mStat = postSnapshot.getValue(ThermoStat.class);
                        movieList.add(mStat);
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

}
