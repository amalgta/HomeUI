package com.styx.gta.homeui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.ui.transformers.ZoomOutPageTransformer;

/**
 * Created by amal.george on 02-11-2016.
 */

public class AddDeviceActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
    }
}
