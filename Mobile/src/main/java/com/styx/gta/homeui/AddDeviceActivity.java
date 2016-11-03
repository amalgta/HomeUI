package com.styx.gta.homeui;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.adapter.ThermoStatAdapter;
import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.model.ThermoStat;
import com.styx.gta.homeui.ui.transformers.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amal.george on 02-11-2016.
 */

public class AddDeviceActivity extends BaseAppCompatActivity {

    private List<ThermoStat> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ThermoStatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DEBUG = true;
        setContentView(R.layout.activity_add_device);
        recyclerView = (RecyclerView) findViewById(R.id.list_device);
        mAdapter = new ThermoStatAdapter(movieList,getmDatabase().child("user").child(getUid()).child("device"));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();

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
        findViewById(R.id.buttonAdd).setOnClickListener(mClickListener);

    }

    private void prepareMovieData() {
        getmDatabase().child("user").child(getUid()).child("device").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movieList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        ThermoStat mStat=postSnapshot.getValue(ThermoStat.class);
                        movieList.add(mStat);
                        mAdapter.notifyDataSetChanged();
                    }catch (Exception e){
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
