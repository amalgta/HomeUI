package com.styx.gta.homeui.ui.view.FontTextView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.styx.gta.homeui.R;

/**
 * Created by amal.george on 18-10-2016.
 */

public class FontTextView extends TextView {
    String TAG=getClass().getCanonicalName();

    String fontname;

    void init() {
        if (fontname == null)
            fontname = getContext().getString(R.string.regular_font_ext);
        getPaint().setTypeface(TypeFaceProvider.getTypeFaceWithExt(getContext(), fontname));
    }

    void processattr(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Font,
                0, 0);
        try {
            fontname = a.getString(R.styleable.Font_font);
            Log.d(TAG, fontname);
        } finally {
            a.recycle();
        }
    }

    public FontTextView(Context context) {
        super(context);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        processattr(attrs);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        processattr(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        processattr(attrs);
        init();
    }
}
