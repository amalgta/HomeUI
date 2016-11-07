package com.styx.gta.homeui.model;

import android.provider.ContactsContract;
import android.provider.SyncStateContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.base.BaseObject;
import com.styx.gta.homeui.util.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.styx.gta.homeui.util.Constants.ACCESS;
import static com.styx.gta.homeui.util.Constants.HOME;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class Home extends BaseObject{
    private String homeID;
    private String homeName;

    public interface USER_ACCESS_PRIVILLEGE {
        String ADMIN = "admin";
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("homeID", homeID);
        result.put("homeName", homeName);
        return result;
    }

    public void setAccess(DatabaseReference mReference, String mUserID, String mAccessPrivillege) {
        mReference.child(HOME).child(homeID).child(USER).child(mUserID).updateChildren((generateMap(new String[]{ACCESS,mAccessPrivillege})));
    }

    public void save() {
        FirebaseDatabase.getInstance().getReference().child(HOME + "/" + homeID).updateChildren(this.toMap());
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public String getHomeID() {
        return homeID;
    }

    public Home() {
    }

    public Home(String homeID, String homeName) {
        this.homeName = homeName;
        this.homeID = homeID;
        save();
    }

    public String getHomeName() {
        return this.homeName;
    }

}
