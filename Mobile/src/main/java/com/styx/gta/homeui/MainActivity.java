package com.styx.gta.homeui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.styx.gta.homeui.base.BaseAppCompatActivity;
import com.styx.gta.homeui.fragment.DevicePagerFragment;
import com.styx.gta.homeui.fragment.HomeFragment;

public class MainActivity extends BaseAppCompatActivity {
    private Button buttonSignout, buttonAddDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_content, new HomeFragment()).commit();

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

    public void swap() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.frame_content, new DevicePagerFragment());
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }

    protected void addDevice() {
        startActivity(new Intent(getApplicationContext(), AddDeviceActivity.class));
    }
}
