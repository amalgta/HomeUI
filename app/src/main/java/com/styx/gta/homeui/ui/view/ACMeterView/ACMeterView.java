package com.styx.gta.homeui.ui.view.ACMeterView;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

import com.styx.gta.homeui.R;

/**
 * Created by amal.george on 19-10-2016.
 */

public class ACMeterView extends View {

    public String meterValue;
    public int meterColor;
    public int meterValueTextColor;

    private Paint circlePaint;

    private void setupView(Context mContext,AttributeSet attrs){
        circlePaint=new Paint();
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ACMeterView, 0, 0);
        try {
            meterValue = a.getString(R.styleable.ACMeterView_meterValue);
            meterColor = a.getColor(R.styleable.ACMeterView_meterColor,Color.BLACK);
            meterValueTextColor = a.getColor(R.styleable.ACMeterView_meterColor,Color.WHITE);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;
        //get the radius as half of the width or height, whichever is smaller
        //subtract ten so that it has some space around it
        int radius = 0;
        if(viewWidthHalf>viewHeightHalf) {
            radius = viewHeightHalf - 10;
        }else {
            radius = viewWidthHalf - 10;
        }
        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);
        //set the paint color using the circle color specified
        circlePaint.setColor(meterColor);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
        //set text properties
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(50);
        circlePaint.setColor(meterValueTextColor);
        //draw the text using the string attribute and chosen properties
        canvas.drawText(String.valueOf(meterValue), viewWidthHalf, viewHeightHalf, circlePaint);
    }

    public String  getMeterValue() {
        return meterValue;
    }

    public void setMeterValue(String meterValue) {
        this.meterValue = meterValue;
        invalidate();
        requestLayout();
    }

    public int getMeterColor() {
        return meterColor;

    }

    public void setMeterColor(int meterColor) {
        this.meterColor = meterColor;
        invalidate();
        requestLayout();
    }

    public ACMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context,attrs);
    }

    public ACMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context,attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ACMeterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(context,attrs);
    }
}
