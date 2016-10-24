package com.styx.gta.homeui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.ui.view.ACMeter.ACMeterView;
import com.styx.gta.homeui.ui.view.FontTextView.FontTextView;

/**
 * Created by amal.george on 19-10-2016.
 */

public class HomeFragment extends Fragment {
    ACMeterView mACMeter;
    FontTextView mPercentage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
        mACMeter = (ACMeterView) rootView.findViewById(R.id.donutChart);
        mPercentage=(FontTextView)rootView.findViewById(R.id.percent) ;
        mACMeter.SetListener(new ACMeterView.RoundKnobButtonListener() {
            @Override
            public void onStateChange(boolean newstate,int percentage) {
                if(newstate){
                    mPercentage.setText("OFF");
                }else {
                    this.onRotate(percentage);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");
                    myRef.setValue("Percentage : "+percentage);
                }
            }

            @Override
            public void onRotate(int percentage) {
                mPercentage.setText(String.valueOf(percentage)+"%");
            }
        });

        return rootView;
    }
}
