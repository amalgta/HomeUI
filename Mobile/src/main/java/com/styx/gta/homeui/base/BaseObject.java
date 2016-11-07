package com.styx.gta.homeui.base;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amal.george on 07-11-2016.
 */

public abstract class BaseObject {
    @Exclude
    public Map<String, Object> toMap(String... mFields) {
        HashMap<String, Object> result = new HashMap<>();
        ArrayList<String> mChildFields = new ArrayList<>(Arrays.asList(mFields));
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if ((mChildFields.size() <= 0) || (mChildFields.contains(field.getName()))) {
                try {
                    result.put(field.getName(), field.get(this).toString());
                } catch (Exception e) {
                    Log.e("GTA_Error", "Illegal Access Error");
                }

            }
        }
        return result;
    }

    @Exclude
    public Map<String, Object> generateMap(String[]... mFields) {
        HashMap<String, Object> result = new HashMap<>();
        for (String[] field : mFields) {
            result.put(field[0], field[1]);
        }
        return result;
    }
}
