package com.styx.gta.homeui.model;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.base.BaseObject;
import com.styx.gta.homeui.interfaces.FireBaseEntity;

import static com.styx.gta.homeui.util.Constants.INSTANCE;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 08-11-2016.
 */

@IgnoreExtraProperties
public class Instance implements FireBaseEntity{
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

    @Override
    public void toMap() {

    }
    @Override
    public void save() {
//        FirebaseDatabase.getInstance().getReference().child(USER).child(userID).child(INSTANCE).child(instanceID).updateChildren(this.toMap());
    }
}