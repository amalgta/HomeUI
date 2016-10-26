package com.styx.gta.homeui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.model.User;
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
    String TAG = "HomeFragment";
    ACMeterView mACMeter;
    FontTextView mPercentage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);

        mACMeter = (ACMeterView) rootView.findViewById(R.id.donutChart);
        mPercentage = (FontTextView) rootView.findViewById(R.id.percent);
        final FontTextView log = (FontTextView) rootView.findViewById(R.id.log);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        mACMeter.SetListener(new ACMeterView.RoundKnobButtonListener() {
            @Override
            public void onStateChange(boolean newstate, int percentage) {
                if (newstate) {
                    myRef.setValue("OFF");
                } else {
                    this.onRotate(percentage);
                }
            }

            @Override
            public void onRotate(int percentage) {
                myRef.setValue(percentage);
            }
        });

        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User mUser = dataSnapshot.getValue(User.class);
                log.setText(mUser.getDisplayName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue().toString();
                if(data!="OFF") {
                    mACMeter.setRotorPercentage(Integer.parseInt(data));
                    mPercentage.setText(data + "%");
                }else {
                    mPercentage.setText(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);
        return rootView;
    }
}
