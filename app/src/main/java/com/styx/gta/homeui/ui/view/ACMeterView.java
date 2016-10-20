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

public class ACMeterView extends View implements OnGestureListener {
    String TAG=getClass().getCanonicalName();

    private String          value;
    private float           radius;
    private int             meterColor;
    private int             meterValueTextColor;

    private GestureDetector 	gestureDetector;
    private boolean 			mState = false;
    private float 				mAngleDown , mAngleUp;

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) return true;
        else return super.onTouchEvent(event);
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
        float x = e.getX() / ((float) getWidth());
        float y = e.getY() / ((float) getHeight());
        mAngleUp = cartesianToPolar(1 - x, 1 - y);// 1- to correct our custom axis direction

        // if we click up the same place where we clicked down, it's just a button press
        if (! Float.isNaN(mAngleDown) && ! Float.isNaN(mAngleUp) && Math.abs(mAngleUp-mAngleDown) < 10) {
            SetState(!mState);
            if (m_listener != null) m_listener.onStateChange(mState);
        }
        return true;
    }
    public void setRotorPosAngle(float deg) {

        if (deg >= 210 || deg <= 150) {
            if (deg > 180) deg = deg - 360;
            Matrix matrix=new Matrix();
            Log.e("GTA",deg+"");//getWidth()/2, getHeight()/2);
        }
    }
    public void setRotorPercentage(int percentage) {
        int posDegree = percentage * 3 - 150;
        if (posDegree < 0) posDegree = 360 + posDegree;
        setRotorPosAngle(posDegree);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float x = e2.getX() / ((float) getWidth());
        float y = e2.getY() / ((float) getHeight());
        float rotDegrees = cartesianToPolar(1 - x, 1 - y);// 1- to correct our custom axis direction

        if (! Float.isNaN(rotDegrees)) {
            // instead of getting 0-> 180, -180 0 , we go for 0 -> 360
            float posDegrees = rotDegrees;
            if (rotDegrees < 0) posDegrees = 360 + rotDegrees;

            // deny full rotation, start start and stop point, and get a linear scale
            if (posDegrees > 210 || posDegrees < 150) {
                // rotate our imageview
                setRotorPosAngle(posDegrees);
                // get a linear scale
                float scaleDegrees = rotDegrees + 150; // given the current parameters, we go from 0 to 300
                // get position percent
                int percent = (int) (scaleDegrees / 3);
                if (m_listener != null) m_listener.onRotate(percent);
                return true; //consumed
            } else
                return false;
        } else
            return false; // not consumed
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public interface RoundKnobButtonListener {
        public void onStateChange(boolean newstate) ;
        public void onRotate(int percentage);
    }
    private RoundKnobButtonListener m_listener;
    public void SetListener(RoundKnobButtonListener l) {
        m_listener = l;
    }
    public void SetState(boolean state) {
        mState = state;
        //ivRotor.setImageBitmap(state?bmpRotorOn:bmpRotorOff);
    }

    private void setupView(Context mContext, AttributeSet attrs) {
        SetState(mState);
        gestureDetector = new GestureDetector(mContext,this);

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
    }
    /**
     * math..
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
