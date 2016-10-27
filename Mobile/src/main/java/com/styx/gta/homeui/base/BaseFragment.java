package com.styx.gta.homeui.base;

/**
 * Created by amal.george on 26-10-2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.styx.gta.homeui.MainActivity;

public class BaseFragment extends Fragment  {
   protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

   public DatabaseReference getmDatabase() {
      return ((BaseAppCompatActivity)getActivity()).getmDatabase();
   }
   public FirebaseAuth getmAuth() {
      return ((BaseAppCompatActivity)getActivity()).getmAuth();
   }
   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   }
}
