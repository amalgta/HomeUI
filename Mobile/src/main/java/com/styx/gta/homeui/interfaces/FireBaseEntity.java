package com.styx.gta.homeui.interfaces;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;
/**
 * Created by amal.george on 08-11-2016.
 */

public interface FireBaseEntity {
    DatabaseReference getDBPath();
    Map<String,Object> toMap();
    void save();
}
