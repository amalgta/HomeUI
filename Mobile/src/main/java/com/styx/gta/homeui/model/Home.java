package com.styx.gta.homeui.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.styx.gta.homeui.base.BaseObject;

import java.util.HashMap;
import java.util.Map;

import static com.styx.gta.homeui.util.Constants.HOME;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class Home extends BaseObject {
    private String homeID;
    private String homeName;

    @Override
    public DatabaseReference getDBPath() {
        return FirebaseDatabase.getInstance().getReference().child(HOME).child(homeID);
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("homeID", homeID);
        result.put("homeName", homeName);
        return result;
    }

    @Override
    public void save() {
        getDBPath().updateChildren(toMap());
    }

    public Home() {
    }

    public Home(String homeID, String homeName) {
        this.homeName = homeName;
        this.homeID = homeID;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setUserAccess(User mUser, String accessVariable) {
        getDBPath().child("/access/" + mUser.getuserID()).setValue(accessVariable);
    }

    public String getUserAccess(DataSnapshot mDataSnapShot) {
        HashMap<String, String> mData = (HashMap<String, String>) mDataSnapShot.getValue();
        return mData.toString();
        //notifyDataSetChanged();
    }
}
