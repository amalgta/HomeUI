package com.styx.gta.homeui.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class User {
    private String userID;
    private String displayName;
    private String email;

    public User() {
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
