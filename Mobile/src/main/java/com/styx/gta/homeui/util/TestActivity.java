package com.styx.gta.homeui.util;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.model.Home;
import com.styx.gta.homeui.model.UserInstance;
import com.styx.gta.homeui.model.User;

import static com.styx.gta.homeui.util.Constants.INSTANCE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 08-11-2016.
 */

public class TestActivity extends BaseAppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        test();
    }

    void test() {
        User mNewUser = new User("USERID", "AMALGTA", "AMALGTA@MAIL.COM");
        addUserInstance(mNewUser);
    }

    void addUserInstance(User mUser) {
        User mNewUser = new User(mUser.getuserID(), Util.usernameFromEmail(mUser.getEmail()), mUser.getEmail());
        UserInstance mCurrentInstance=new UserInstance(getmDatabase().child(USER+"/"+mNewUser.getuserID()+"/"+INSTANCE).push().getKey(),Util.getAppInstallUniqueID(getApplicationContext()),mNewUser);
        getmDatabase().child(USER+"/"+mNewUser.getuserID()+"/"+INSTANCE).child(mCurrentInstance.getInstanceID()).setValue(mCurrentInstance);
    }

    void addHome(User mUser, Home mHome, String mAccessLevel) {
        mUser.addHome(mHome, mAccessLevel);
        mHome.setUserAccess(mUser, mAccessLevel);
    }
}
