package com.styx.gta.homeui;

import android.content.Intent;
import android.os.Bundle;

import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.util.TestActivity;

public class SplashScreenActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getmAuth().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), TestActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        finish();
    }
}
