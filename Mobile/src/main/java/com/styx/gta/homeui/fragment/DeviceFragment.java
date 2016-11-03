package com.styx.gta.homeui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.base.BaseFragment;
import com.styx.gta.homeui.model.ThermoStat;
import com.styx.gta.homeui.ui.view.ACMeter.ACMeterView;
import com.styx.gta.homeui.ui.view.FontTextView.FontTextView;
import com.styx.gta.homeui.util.Constants;

import static com.styx.gta.homeui.util.Constants.DEVICE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 19-10-2016.
 */

public class DeviceFragment extends BaseFragment {
    ACMeterView mACMeter;
    FontTextView mPercentage;
    String mObjectId;
    DatabaseReference myRef;

    public DeviceFragment() {
        mObjectId = "-KVcwmPQGVqPC1Jbb9_Y";
    }

    public void setBundle(Bundle mBundle) {
        mObjectId = (String) mBundle.get("ObjectID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_device, container, false);
        myRef = getmDatabase().child(USER).child(getmAuth().getCurrentUser().getUid()).child(DEVICE).child(mObjectId);
        mACMeter = (ACMeterView) rootView.findViewById(R.id.donutChart);
        mPercentage = (FontTextView) rootView.findViewById(R.id.percent);
        final FontTextView log = (FontTextView) rootView.findViewById(R.id.log);

        mACMeter.SetListener(new ACMeterView.RoundKnobButtonListener() {
            @Override
            public void onStateChange(boolean newstate, int percentage) {
                if (newstate) {
                    myRef.child("thermostatValue").setValue("OFF");
                } else {
                    this.onRotate(percentage);
                }
            }

            @Override
            public void onRotate(int percentage) {
                myRef.child("thermostatValue").setValue(percentage + "");
            }
        });

        getmDatabase().child("user").child(user.getUid()).child("device").child(mObjectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    ThermoStat mThermoStat = dataSnapshot.getValue(ThermoStat.class);
                    Log.e("GTA", dataSnapshot.toString());
                    log.setText(mThermoStat.getThermostatName());
                    if (!mThermoStat.getThermostatValue().equals("OFF")) {
                        mACMeter.setRotorPercentage(Integer.parseInt(mThermoStat.getThermostatValue()));
                        mPercentage.setText(mThermoStat.getThermostatValue() + "%");
                    } else {
                        mPercentage.setText(mThermoStat.getThermostatValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
        return rootView;
    }
}
