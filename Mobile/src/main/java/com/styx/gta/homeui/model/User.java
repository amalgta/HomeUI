package com.styx.gta.homeui.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.base.BaseObject;
import com.styx.gta.homeui.interfaces.FireBaseEntity;
import com.styx.gta.homeui.model.Home;

import java.util.HashMap;
import java.util.Map;

import static com.styx.gta.homeui.util.Constants.HOME;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class User extends BaseObject{
    private String userID;
    private String displayName;
    private String email;

    public class Constants{

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

    @Override
    public DatabaseReference getDBPath() {
        return FirebaseDatabase.getInstance().getReference().child(USER).child(userID);
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("displayName", displayName);
        result.put("email", email);
        return result;
    }

    @Override
    public void save() {
        getDBPath().updateChildren(toMap());
    }

    public User(String userID, String displayName, String email) {
        this.userID = userID;
        this.displayName = displayName;
        this.email = email;
    }
    public User() {

    }

    public void addAppInstance(UserInstance mUserInstance){

    }
    public void addHome(Home newHome, String mAccessLevel){
        getDBPath().child(HOME).child(newHome.getHomeID()+"/access").setValue(mAccessLevel);
    }
}
