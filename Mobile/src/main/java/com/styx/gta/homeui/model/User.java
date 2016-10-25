package com.styx.gta.homeui.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class User {
    public String providerID;
    public String displayName;
    public String email;

    public User() {
    }

    public User(String providerID, String displayName, String email) {
        this.providerID = providerID;
        this.displayName = displayName;
        this.email = email;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
