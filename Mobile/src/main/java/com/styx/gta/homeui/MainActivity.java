package com.styx.gta.homeui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.fragment.HomeFragment;
import com.styx.gta.homeui.model.ThermoStat;
import com.styx.gta.homeui.ui.transformers.ZoomOutPageTransformer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends BaseAppCompatActivity {

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
    private Button buttonSignout, buttonAddDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        buttonSignout = (Button) findViewById(R.id.buttonSignout);
        buttonAddDevice = (Button) findViewById(R.id.buttonAddDevice);
        buttonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        buttonAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDevice();
            }
        });
    }

    protected void addDevice() {
        startActivity(new Intent(getApplicationContext(), AddDeviceActivity.class));
    }

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
            if (mThermoStatList.size() <= 0) {
                return new HomeFragment();
            } else {
                HomeFragment homeFragment = new HomeFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("ObjectID", mThermoStatList.get(position).getThermostatID());
                homeFragment.setBundle(mBundle);
                return homeFragment;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
