package com.styx.gta.homeui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.styx.gta.homeui.LoginActivity;

/**
 * Created by amal.george on 26-10-2016.
 */

public class BaseAppCompatActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {


    protected String TAG;
    protected boolean DEBUG;

    protected DatabaseReference mDatabase;
    protected FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    protected BaseAppCompatActivity() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        TAG = this.getClass().getCanonicalName();
        DEBUG = true;
    }

    protected void debug(String mLogText) {
        if (DEBUG) {
            Log.e(TAG, mLogText);
        }
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
