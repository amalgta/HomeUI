package com.styx.gta.homeui.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import static com.styx.gta.homeui.util.Constants.HOME;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class User {
    private String userID;
    private String displayName;
    private String email;

    public interface HOME_STATUS{
        boolean ACTIVE_HOME = true;
        boolean INACTIVE_HOME= true;
    }

    public User() {
    }
    public void addAppInstance(String mAppInstance){
        FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child("instance").setValue(mAppInstance);
    }
    public void addHome(String mHomeID,boolean mHomeStatus){
        FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child(HOME).child(mHomeID).child("active").setValue(mHomeStatus);
    }
    public void save(DatabaseReference mDatabaseReference){
        mDatabaseReference.child(USER).child(this.getuserID()).setValue(this);
    }

    public User(String userID, String displayName, String email) {
        this.userID = userID;
        this.displayName = displayName;
        this.email = email;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getuserID() {
        return userID;
    }
}
