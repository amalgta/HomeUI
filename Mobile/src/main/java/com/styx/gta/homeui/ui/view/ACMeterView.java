package com.styx.gta.homeui.ui.view;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
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
import android.widget.ImageView;

import com.styx.gta.homeui.R;


/**
 * Created by amal.george on 19-10-2016.
 */

/**
 * GTA Notes
 * <p>
 * Input Expected:
 * MinValue=2
 * MaxValue=200
 * value=100
 * radius=60
 * meterColor=COLOR
 * SweepAngle=45
 * meterValueTextColor
 * <p>
 * Rules :
 * MaxValue>=CurrentValue>=MinValue
 * MinValue<MaxValue
 * Constants :
 * MaxDegree=359.9f
 * <p>
 * Calculation Variables:
 * ConceptualMax=(MaxValue-MinValue)
 * ConceptualMin=0
 * <p>
 * CurrentPercentageFromValue=( (CurrentValue/ConceptualMax) * 100)
 * CurrentValueFromPercentage=( ( (CurrentPercentage/100) * ConceptualMax) ) + MinValue)
 * <p>
 * <p>
 * initMaxDegree=(SweepAngle/2)
 * initMinDegree=(MaxDegree - (SweepAngle/2))
 * CurrentPercentageFromDegree=( (CurrentDegree*100) / SweepAngle)
 * CurrentConceptualDegreeFromPercentage=( (Percentage*SweepAngle) / 100)
 * CurrentRepresentationalDegree=( (initMinDegree+CurrentConceptualDegreeFromPercentage) % MaxDegree)
 **/


public class ACMeterView extends View implements OnGestureListener {
    String TAG = getClass().getCanonicalName();

    //UI Dimensions
    private float value, minValue, maxValue;
    private float radius, sweepAngle;
    private int meterColor, meterValueTextColor;


    private float currentOffset;

    private GestureDetector gestureDetector;
    private boolean mState = false;
    private float mAngleDown, mAngleUp;

    float adjust = 90f;


    private void initializeConstants(Context mContext, AttributeSet attrs) {
        //SetState(mState); {Can be used to implement degree/fahrenheit transformation}

        //Defaults
        final float RADIUS = 80.0f;
        final float SWEEP = 120.0f;
        final float MINVALUE = 0f;
        final float MAXVALUE = 100.0f;
        final int DEFAULT_METER_COLOR = Color.BLACK;
        final int DEFAULT_METERVALUETEXT_COLOR = Color.WHITE;

        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ACMeterView, 0, 0);
        try {
            value = a.getFloat(R.styleable.ACMeterView_value, ((MINVALUE + MAXVALUE) / 2));
            minValue = a.getFloat(R.styleable.ACMeterView_minValue, MINVALUE);
            maxValue = a.getFloat(R.styleable.ACMeterView_maxValue, MAXVALUE);
            radius = a.getDimension(R.styleable.ACMeterView_radius, RADIUS);
            sweepAngle = a.getDimension(R.styleable.ACMeterView_sweepAngle, SWEEP);
            meterColor = a.getColor(R.styleable.ACMeterView_meterColor, DEFAULT_METER_COLOR);
            meterValueTextColor = a.getColor(R.styleable.ACMeterView_meterValueTextColor, DEFAULT_METERVALUETEXT_COLOR);
        } finally {
            a.recycle();
        }
        //Validate
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        float x = e.getX() / ((float) getWidth());
        float y = e.getY() / ((float) getHeight());
        mAngleDown = cartesianToPolar(1 - x, 1 - y);// 1- to correct our custom axis direction
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        float x = (e2.getX() / ((float) getWidth()));
        float y = (e2.getY() / ((float) getHeight()));
        float rotDegrees = cartesianToPolar(1 - x, 1 - y);

        if (!Float.isNaN(rotDegrees)) {
            float posDegrees = rotDegrees;
            posDegrees -= adjust;
            if (rotDegrees < adjust) {
                posDegrees = 360 + rotDegrees - adjust;
            }
            setRotorPosAngle(posDegrees);
            return true;
        } else
            return false;
    }


    public void setRotorPosAngle(float deg) {
        Log.e(TAG, "Deg " + deg);//0-360
            currentOffset = deg;
            invalidate();
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public interface RoundKnobButtonListener {
        public void onStateChange(boolean newstate);

        public void onRotate(int percentage);
    }

    private RoundKnobButtonListener m_listener;


    public void SetState(boolean state) {
        mState = state;
        //ivRotor.setImageBitmap(state?bmpRotorOn:bmpRotorOff);
    }

    /**
     * math..
     *
     * @param x
     * @param y
     * @return
     */
    private float cartesianToPolar(float x, float y) {
        return (float) -Math.toDegrees(Math.atan2(x - 0.5f, y - 0.5f));
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

    void actualDraw(Canvas mCanvas) {
        float mRotationIndex = currentOffset;
        RectF mBaseRectangle = new RectF();
        float mOffset = .20f * radius;

        //Paint Setup - Base
        Paint mPaintBase = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBase.setDither(true);
        mPaintBase.setStyle(Style.STROKE);
        mPaintBase.setStrokeCap(Paint.Cap.BUTT);
        mPaintBase.setStrokeWidth(radius / 6.0f);
        mPaintBase.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, radius - 5, 0xff2D293E, 0xff252437, Shader.TileMode.CLAMP));

        mBaseRectangle.set(mOffset, mOffset, (radius * 2) - mOffset, (radius * 2) - mOffset);
        Path mBasePath = new Path();
        mBasePath.addCircle(mBaseRectangle.centerX(), mBaseRectangle.centerY(), radius - mOffset, Path.Direction.CCW);


        //Paint Setup - Base
        Paint mPaintCold = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCold.setDither(true);
        mPaintCold.setStyle(Style.STROKE);
        mPaintCold.setStrokeCap(Paint.Cap.BUTT);
        mPaintCold.setStrokeWidth(radius / 6.0f);
        mPaintCold.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, radius - 5, 0xffFFFFFF, 0xffEFEFEF, Shader.TileMode.CLAMP));


        Path mColdPath = new Path();
        mColdPath.arcTo(mBaseRectangle, -20, 20, false);

        //Paint Setup - Index
        Paint mPaintIndex = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintIndex.setDither(true);
        mPaintIndex.setStyle(Style.STROKE);
        mPaintIndex.setStrokeCap(Paint.Cap.BUTT);
        mPaintIndex.setStrokeWidth(radius / 6.0f);
        mPaintIndex.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, radius - 5, 0xff3D8DD4, 0xff4AAAFF, Shader.TileMode.CLAMP));

        Path mIndexPath = new Path();
        mIndexPath.arcTo(mBaseRectangle, mRotationIndex, 2, false);


        //Paint Setup - Warm
        Paint mPaintWarm = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWarm.setDither(true);
        mPaintWarm.setStyle(Style.STROKE);
        mPaintWarm.setStrokeCap(Paint.Cap.BUTT);
        mPaintWarm.setStrokeWidth(radius / 6.0f);
        mPaintWarm.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, radius - 5, 0xffF20884, 0xffF20884, Shader.TileMode.CLAMP));

        Path mWarmPath = new Path();
        mWarmPath.arcTo(mBaseRectangle, sweepAngle, 10, false);


        //Canvas Draw

        mCanvas.drawPath(mBasePath, mPaintBase);
        mCanvas.drawPath(mColdPath, mPaintCold);
        mCanvas.drawPath(mWarmPath, mPaintWarm);
        mCanvas.drawPath(mIndexPath, mPaintIndex);
    }

    private void init(Context context, AttributeSet attrs) {
        gestureDetector = new GestureDetector(context, this);
        initializeConstants(context, attrs);
    }

    public ACMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ACMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ACMeterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
}
