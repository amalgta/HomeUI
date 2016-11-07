package com.styx.gta.homeui.model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public interface HOME_STATUS {
        boolean ACTIVE_HOME = true;
        boolean INACTIVE_HOME = true;
    }

    public User() {
    }

    @Exclude
    public Map<String, Object> toMap(String... mFields) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("displayName", displayName);
        result.put("email", email);
        return result;
    }

    /*    public Map<String, Object> toMapTest(String[]... mFields) {
            HashMap<String, Object> result = new HashMap<>();
            ArrayList<String> mChildFields = new ArrayList<>(Arrays.asList(mFields));
            for (Field field : this.getClass().getDeclaredFields()) {
                if (mChildFields.contains(field.getName())) {
                    try {
                        result.put(field.getName(), field.get(this).toString());
                    } catch (Exception e) {
                        Log.e("GTA_Error", "Illegal Access Error");
                    }

                }
            }
            return result;
        }*/
    public Map<String, Object> generateMap(String[]... mFields) {
        HashMap<String, Object> result = new HashMap<>();
        for (String[] field : mFields) {
            result.put(field[0],field[1]);
            }
        return result;
    }

    public void addAppInstance(String mAppInstance) {
        FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child("instance").setValue(mAppInstance);
    }

    public void addHome(String mHomeID, boolean mHomeStatus) {
        FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child(HOME).child(mHomeID).child("active").setValue(mHomeStatus);
    }

    public void save() {
        FirebaseDatabase.getInstance().getReference().child(USER).child(this.getuserID()).updateChildren(this.toMap());
    }

    public void tempSave() {
        FirebaseDatabase.getInstance().getReference().child(USER).child(this.getuserID()).updateChildren(generateMap(new String[] { "One", "Two"}));
    }

    public User(String userID, String displayName, String email) {
        this.userID = userID;
        this.displayName = displayName;
        this.email = email;
        save();
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
