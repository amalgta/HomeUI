package com.styx.gta.homeui.fragment;

import com.styx.gta.homeui.ui.view.ACMeterView.RoundKnobButtonListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.styx.gta.homeui.R;
import com.styx.gta.homeui.ui.view.ACMeterView;

/**
 * Created by amal.george on 19-10-2016.
 */

public class HomeFragment extends Fragment {
    ACMeterView mACMeter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
        mACMeter = (ACMeterView) rootView.findViewById(R.id.donutChart);
        return rootView;
    }
}
