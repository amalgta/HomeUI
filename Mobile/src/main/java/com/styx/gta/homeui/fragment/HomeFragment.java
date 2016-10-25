package com.styx.gta.homeui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.ui.view.ACMeter.ACMeterView;
import com.styx.gta.homeui.ui.view.FontTextView.FontTextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import static android.content.ContentValues.TAG;

/**
 * Created by amal.george on 19-10-2016.
 */

public class HomeFragment extends Fragment {
    String TAG="HomeFragment";
    ACMeterView mACMeter;
    FontTextView mPercentage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
//        Bundle params = new Bundle();
//        params.putString( FirebaseAnalytics.Param.ITEM_ID, "Name" );
//        params.putString( FirebaseAnalytics.Param.ITEM_CATEGORY, "icon" );
//        params.putLong( FirebaseAnalytics.Param.VALUE,2 );
//        mFirebaseAnalytics.logEvent("APP_START",params);

        mACMeter = (ACMeterView) rootView.findViewById(R.id.donutChart);
        mPercentage=(FontTextView)rootView.findViewById(R.id.percent) ;

        mACMeter.SetListener(new ACMeterView.RoundKnobButtonListener() {
            @Override
            public void onStateChange(boolean newstate,int percentage) {
                if(newstate){
                    mPercentage.setText("OFF");
                }else {
                    this.onRotate(percentage);

                }
            }

            @Override
            public void onRotate(int percentage) {
                mPercentage.setText(String.valueOf(percentage)+"%");
                myRef.setValue(percentage);
            }
        });
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String data = dataSnapshot.getValue().toString();
                    mACMeter.setRotorPercentage(Integer.parseInt(data));
                    mPercentage.setText(data+"%");

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(postListener);
        return rootView;
    }
}
