package com.styx.gta.homeui.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.base.BaseObject;
import com.styx.gta.homeui.interfaces.FireBaseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.styx.gta.homeui.util.Constants.HOME;
import static com.styx.gta.homeui.util.Constants.INSTANCE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 08-11-2016.
 */

public class UserInstance extends BaseObject {
    String instanceID;
    String appInstanceID;
    User currentUser;
    UserInstance(){}
    public UserInstance(String instanceID, String appInstanceID,User mUser) {
        this.instanceID = instanceID;
        this.appInstanceID = appInstanceID;
        this.currentUser=mUser;
    }

    public String getAppInstanceID() {
        return appInstanceID;
    }

    public String getInstanceID() {
        return instanceID;
    }

    @Override
    public DatabaseReference getDBPath() {
        return null;
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("appInstanceID", appInstanceID);
        result.put("instanceID", instanceID);
        return result;
    }

    @Override
    public void save() {

    }
}