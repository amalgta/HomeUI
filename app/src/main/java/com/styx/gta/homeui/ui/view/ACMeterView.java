package com.styx.gta.homeui.ui.view;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
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
    String TAG=getClass().getCanonicalName();

    private String          value;
    private float           radius;
    private int             meterColor;
    private int             meterValueTextColor;


    public void rotateDialer(float angle){
        Log.d(TAG,"Angle : "+angle);
    }
    public class  ACMeterTouchListener implements OnTouchListener{
        private double startAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.e("GTA",event.toString());

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startAngle = getAngle(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    double currentAngle = getAngle(event.getX(), event.getY());
                    rotateDialer((float) (startAngle - currentAngle));
                    startAngle = currentAngle;
                    break;

                case MotionEvent.ACTION_UP:

                    break;
            }
            return false;
        }
        /**
         * @return The angle of the unit circle with the image view's center
         */
        private double getAngle(double xTouch, double yTouch) {
            double x = xTouch - (getWidth() / 2d);
            double y = getHeight() - yTouch - (getHeight() / 2d);

            switch (getQuadrant(x, y)) {
                case 1:
                    return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
                case 2:
                    return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
                case 3:
                    return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
                case 4:
                    return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
                default:
                    return 0;
            }
        }

        /**
         * @return The selected quadrant.
         */
        private int getQuadrant(double x, double y) {
            if (x >= 0) {
                return y >= 0 ? 1 : 4;
            } else {
                return y >= 0 ? 2 : 3;
            }
        }
    }
    private void setupView(Context mContext, AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ACMeterView, 0, 0);
        try {
            value = a.getString(R.styleable.ACMeterView_value);
            radius = a.getDimension(R.styleable.ACMeterView_radius, 20.0f);
            meterColor = a.getColor(R.styleable.ACMeterView_meterColor, Color.BLACK);
            meterValueTextColor = a.getColor(R.styleable.ACMeterView_textColor, Color.WHITE);
        } finally {
            a.recycle();
        }
        setOnTouchListener(new ACMeterTouchListener());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //radius*2
        int desiredWidth = (int) radius * 2;
        int desiredHeight = (int) radius * 2;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //70dp exact
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //wrap content
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        actualDraw(canvas);
    }
    void actualDraw(Canvas mCanvas){

        RectF mBaseRectangle=new RectF();
        float mOffset = .20f * radius;

        //Paint Setup - Base
        Paint mPaintBase = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBase.setDither(true);
        mPaintBase.setStyle(Style.STROKE);
        mPaintBase.setStrokeCap(Paint.Cap.BUTT);
        mPaintBase.setStrokeWidth(radius / 6.0f);
        mPaintBase.setShader(new RadialGradient(getWidth()/2,getHeight()/2, radius-5,0xff2D293E,0xff252437,Shader.TileMode.CLAMP));

        mBaseRectangle.set(mOffset, mOffset, (radius*2)-mOffset, (radius*2)-mOffset);
        Path mBasePath = new Path();
        mBasePath.arcTo(mBaseRectangle, (290+20), (340), false);


        //Paint Setup - Base
        Paint mPaintCold=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCold.setDither(true);
        mPaintCold.setStyle(Style.STROKE);
        mPaintCold.setStrokeCap(Paint.Cap.BUTT);
        mPaintCold.setStrokeWidth(radius / 6.0f);
        mPaintCold.setShader(new RadialGradient(getWidth()/2,getHeight()/2, radius-5,0xffFFFFFF,0xffEFEFEF,Shader.TileMode.CLAMP));


        Path mColdPath = new Path();
        mColdPath.arcTo(mBaseRectangle, 290, 20, false);

        //Paint Setup - Index
        Paint mPaintIndex=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintIndex.setDither(true);
        mPaintIndex.setStyle(Style.STROKE);
        mPaintIndex.setStrokeCap(Paint.Cap.BUTT);
        mPaintIndex.setStrokeWidth(radius / 6.0f);
        mPaintIndex.setShader(new RadialGradient(getWidth()/2,getHeight()/2, radius-5,0xff3D8DD4,0xff4AAAFF,Shader.TileMode.CLAMP));

        Path mIndexPath = new Path();
        mIndexPath.arcTo(mBaseRectangle, 359, 2, false);


        //Paint Setup - Warm
        Paint mPaintWarm=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWarm.setDither(true);
        mPaintWarm.setStyle(Style.STROKE);
        mPaintWarm.setStrokeCap(Paint.Cap.BUTT);
        mPaintWarm.setStrokeWidth(radius / 6.0f);
        mPaintWarm.setShader(new RadialGradient(getWidth()/2,getHeight()/2, radius-5,0xffF20884,0xffF20884,Shader.TileMode.CLAMP));

        Path mWarmPath = new Path();
        mWarmPath.arcTo(mBaseRectangle, 30, 10, false);


        //Canvas Draw
        mCanvas.drawPath(mBasePath,mPaintBase);
        mCanvas.drawPath(mColdPath,mPaintCold);
        mCanvas.drawPath(mIndexPath,mPaintIndex);
        mCanvas.drawPath(mWarmPath,mPaintWarm);
    }

    public ACMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context, attrs);
    }

    public ACMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ACMeterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView(context, attrs);
    }
}
