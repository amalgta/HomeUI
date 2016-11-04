package com.styx.gta.homeui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.base.BaseFragment;
import com.styx.gta.homeui.model.device.ThermoStat;
import com.styx.gta.homeui.ui.transformers.ZoomOutPageTransformer;

import java.util.ArrayList;

/**
 * Created by amal.george on 03-11-2016.
 */

public class DevicePagerFragment extends BaseFragment {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private int NUM_PAGES = 0;
    private ArrayList<ThermoStat> mThermoStatList;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view_acmeterview_background pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_device_pager, container, false);
        DatabaseReference mUserDevices = getmDatabase().child("user").child(getUid()).child("device");
        mThermoStatList = new ArrayList<>();
        mUserDevices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NUM_PAGES = ((int) dataSnapshot.getChildrenCount());
                mThermoStatList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ThermoStat mStat = postSnapshot.getValue(ThermoStat.class);
                    mThermoStatList.add(mStat);
                }
                mPagerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return rootView;

    }

/*
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
*/

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DeviceFragment deviceFragment = new DeviceFragment();
            Bundle mBundle = new Bundle();
            mBundle.putString("ObjectID", mThermoStatList.get(position).getThermostatID());
            deviceFragment.setBundle(mBundle);
            return deviceFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
