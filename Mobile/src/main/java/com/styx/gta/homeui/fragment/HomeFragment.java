package com.styx.gta.homeui.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.MainActivity;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.base.BaseFragment;
import com.styx.gta.homeui.model.ThermoStat;
import com.styx.gta.homeui.ui.view.ACMeter.ACMeterView;
import com.styx.gta.homeui.ui.view.FontTextView.FontTextView;

import static com.styx.gta.homeui.util.Constants.DEVICE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 19-10-2016.
 */

public class HomeFragment extends BaseFragment {
    FontTextView textViewDeviceStatus;
    Button buttonManageDevice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
        textViewDeviceStatus = (FontTextView) rootView.findViewById(R.id.textViewDeviceStatus);
        buttonManageDevice = (Button) rootView.findViewById(R.id.buttonManageDevice);
        DatabaseReference mDevices = getmDatabase().child(USER).child(getUid()).child(DEVICE);
        buttonManageDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).swap();
            }
        });
        mDevices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    buttonManageDevice.setVisibility(View.INVISIBLE);
                    textViewDeviceStatus.setText("No configured device.");
                } else if (dataSnapshot.getChildrenCount() == 1) {
                    buttonManageDevice.setVisibility(View.VISIBLE);
                    textViewDeviceStatus.setText(dataSnapshot.getChildrenCount() + " device configured.");
                } else if (dataSnapshot.getChildrenCount() > 1) {
                    textViewDeviceStatus.setText(dataSnapshot.getChildrenCount() + " devices configured.");
                    buttonManageDevice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }
}
