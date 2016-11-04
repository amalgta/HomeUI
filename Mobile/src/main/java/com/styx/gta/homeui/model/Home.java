package com.styx.gta.homeui.model;

import android.provider.ContactsContract;
import android.provider.SyncStateContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.util.Constants;

import static com.styx.gta.homeui.util.Constants.ACCESS;
import static com.styx.gta.homeui.util.Constants.HOME;
import static com.styx.gta.homeui.util.Constants.USER;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class Home {
    private String homeID;
    private String homeName;

    public interface ACCESS_PRIVILLEGE {
        String ADMIN = "admin";
    }

    public void setAccess(DatabaseReference mReference, String mUserID, String mAccessPrivillege) {
        mReference.child(HOME).child(homeID).child(USER).child(mUserID).child(ACCESS).setValue(mAccessPrivillege);
    }
    public void save(DatabaseReference mReference){
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public Home() {
    }

    public Home(String homeName) {
        this.homeName = homeName;
    }

    public String getHomeName() {
        return this.homeName;
    }

}
