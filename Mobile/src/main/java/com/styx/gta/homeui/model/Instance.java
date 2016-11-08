package com.styx.gta.homeui.model;

import com.google.firebase.database.DatabaseReference;
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

@IgnoreExtraProperties
public class Instance extends BaseObject {
    String instanceID;
    String appInstanceID;

    Instance(String instanceID, String appInstanceID) {
        this.instanceID = instanceID;
        this.appInstanceID = appInstanceID;
    }

    Instance() {

    }

    @Override
    public DatabaseReference getDBPath() {
        return FirebaseDatabase.getInstance().getReference().child(INSTANCE);
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("instanceID", instanceID);
        result.put("appInstanceID", appInstanceID);
        return result;
    }

    @Override
    public void save() {

    }
}