package com.styx.gta.homeui.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class User {
    private String providerID;
    private String displayName;
    private String email;
    private ArrayList<ThermoStat> userThermoStats;

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
