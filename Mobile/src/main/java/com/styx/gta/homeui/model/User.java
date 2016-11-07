package com.styx.gta.homeui.model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.base.BaseObject;
import com.styx.gta.homeui.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.styx.gta.homeui.util.Constants.HOME;
import static com.styx.gta.homeui.util.Constants.INSTANCE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class User {
    private String userID;
    private String displayName;
    private String email;

    public String getDisplayName() {
        return this.displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getuserID() {
        return userID;
    }

    public void save() {
        FirebaseDatabase.getInstance().getReference().child(USER).child(this.getuserID()).updateChildren(this.toMap());
    }

    public interface HOME_STATUS {
        String ACTIVE_HOME = "true";
    }

    public void addAppInstance(String mAppInstance) {
        new Instance(FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child(INSTANCE).push().getKey(), mAppInstance);
    }

    public void addHome(String mHomeID, String mHomeStatus) {
        FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child(HOME).child(mHomeID).updateChildren(generateMap(new String[]{"active",mHomeStatus}));
    }


    public User(String userID, String displayName, String email) {
        this.userID = userID;
        this.displayName = displayName;
        this.email = email;
        save();
    }


    @IgnoreExtraProperties
    class Instance extends BaseObject {
        String instanceID;
        String appInstanceID;
        String activeHome;

        Instance(String instanceID, String appInstanceID) {
            this.instanceID = instanceID;
            this.appInstanceID=appInstanceID;
            save();
        }
        Instance(){

        }
        public void setAppInstanceID(String appInstanceID) {
            this.appInstanceID = appInstanceID;
        }

        public void setActiveHome(String activeHome) {
            this.activeHome = activeHome;
        }

        private void save() {
            FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child(INSTANCE).child(instanceID).updateChildren(this.toMap());
        }
    }
}
