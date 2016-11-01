package com.styx.gta.homeui.app;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by amal.george on 01-11-2016.
 */

public class StyxMobile extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
