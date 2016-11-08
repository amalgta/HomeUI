package com.styx.gta.homeui.util;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.styx.gta.homeui.R;
import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.model.Home;
import com.styx.gta.homeui.model.User;
import com.styx.gta.homeui.ui.view.FontTextView.FontTextView;

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
        String mUser = "USERID";
        String mEmail = "AMALGTA@AMAL.COM";
        User mNewUser = new User("USERID", "AMALGTA", "AMALGTA@MAIL.COM");
        User mNewUser2 = new User("USERID2", "AMALGTA2", "AMALGTA2@MAIL.COM");
        Home mHome1 = new Home("HOMEID", "PARADISE");
        Home mHome2 = new Home("HOMEID2", "PARADISE2");
        addHome(mNewUser,mHome1, "admin");
        addHome(mNewUser,mHome2, "admin");
        addHome(mNewUser2,mHome1, "admin");
        String result="mResult: ";
        mNewUser2.getDBPath().child("home").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((FontTextView)findViewById(R.id.test_log)).setText(dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void addHome(User mUser, Home mHome, String mAccessLevel) {
        mUser.addHome(mHome, mAccessLevel);
        mHome.setUserAccess(mUser, mAccessLevel);
    }
}
