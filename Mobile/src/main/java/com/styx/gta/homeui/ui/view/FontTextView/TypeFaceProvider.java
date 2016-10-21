package com.styx.gta.homeui.ui.view.FontTextView;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by amal.george on 18-10-2016.
 */

public class TypeFaceProvider
{

    public static final String TYPEFACE_FOLDER = "fonts";
    public static final String TYPEFACE_EXTENSION = ".ttf";

    private static Hashtable<String, Typeface> sTypeFaces = new Hashtable<String, Typeface>(
    );

    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            String fontPath = new StringBuilder(TYPEFACE_FOLDER).append('/').append(fileName).toString();
            tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            sTypeFaces.put(fileName, tempTypeface);
        }

        return tempTypeface;
    }
    public static Typeface getTypeFaceWithExt(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            String fontPath = new StringBuilder(fileName).toString();
            if(context.getAssets()!=null)
                tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            else
                tempTypeface=Typeface.DEFAULT;
            sTypeFaces.put(fileName, tempTypeface);
        }

        return tempTypeface;
    }
}