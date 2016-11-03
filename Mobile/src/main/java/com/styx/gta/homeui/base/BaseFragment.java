package com.styx.gta.homeui.base;

/**
 * Created by amal.george on 26-10-2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class BaseFragment extends Fragment{
    protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    protected String TAG;
    protected boolean DEBUG;

    public DatabaseReference getmDatabase() {
        return ((BaseAppCompatActivity) getActivity()).getmDatabase();
    }

    public FirebaseAuth getmAuth() {
        return ((BaseAppCompatActivity) getActivity()).getmAuth();
    }

    protected void debug(String mLogText) {
        if (DEBUG) {
            Log.e(TAG, mLogText);
        }
    }

    public BaseFragment() {
        TAG = this.getClass().getCanonicalName();
        DEBUG = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
