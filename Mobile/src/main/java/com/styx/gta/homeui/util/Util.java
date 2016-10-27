package com.styx.gta.homeui.util;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by amal.george on 26-10-2016.
 */

public abstract class Util {
    public static String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    public void isLoggedIn(){
    }
}
