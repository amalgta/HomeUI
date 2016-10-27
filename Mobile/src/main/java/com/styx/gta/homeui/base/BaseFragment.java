package com.styx.gta.homeui.base;

/**
 * Created by amal.george on 26-10-2016.
 */
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseFragment extends Fragment  {
   protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

}
