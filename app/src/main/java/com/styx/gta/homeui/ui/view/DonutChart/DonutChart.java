package com.styx.gta.homeui.ui.view.DonutChart;

import android.view.View;

/**
 * Created by amal.george on 19-10-2016.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import com.styx.gta.homeui.R;

public class DonutChart extends View {


    private float radius;

    Paint paint;
    Paint shadowPaint;

    Path myPath;
    Path shadowPath;

    RectF outterCircle;
    RectF innerCircle;
    RectF shadowRectF;

    public DonutChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DonutChart,
                0, 0
        );

        try {
            radius = a.getDimension(R.styleable.DonutChart_radius, 20.0f);
        } finally {
            a.recycle();
        }

        paint = new Paint();
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(radius / 14.0f);

        shadowPaint = new Paint();
        shadowPaint.setColor(0xf0000000);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStrokeWidth(6.0f);
        shadowPaint.setMaskFilter(new BlurMaskFilter(4, BlurMaskFilter.Blur.SOLID));


        myPath = new Path();
        shadowPath = new Path();


        outterCircle = new RectF();
        innerCircle = new RectF();
        shadowRectF = new RectF();

        float adjust = (.019f*radius);
        shadowRectF.set(adjust, adjust, radius*2-adjust, radius*2-adjust);

        adjust = .038f * radius;
        outterCircle.set(adjust, adjust, radius*2-adjust, radius*2-adjust);

        adjust = .276f * radius;
        innerCircle.set(adjust, adjust, radius*2-adjust, radius*2-adjust);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuterPos(canvas,paint,0);

        // draw shadow
        paint.setShader(null);
        float adjust = (.0045f*radius);
        paint.setShadowLayer(8, adjust, -adjust, 0xaa000000);
        // yellow
        setGradient(0xff2D293E,0xff252437);
        drawDonut(canvas,paint, 180,359.9f);

        setGradient(0xffF20884,0xffD40880);
        drawDonut(canvas,paint, 15,7f);

        setGradient(0xffFFFFFF,0xffCDCDCD);
        drawDonut(canvas,paint, 280,7f);

        setGradient(0xff3D8DD4,0xff4AAAFF);
        drawDonut(canvas,paint, 359,2f);

    }
    public void drawOuterPos(Canvas canvas, Paint paint, float start){

    }

    public void drawDonut(Canvas canvas, Paint paint, float start,float sweep){

        myPath.reset();
        myPath.arcTo(outterCircle, start, sweep, false);
        myPath.arcTo(innerCircle, start+sweep, -sweep, false);
        myPath.close();
        canvas.drawPath(myPath, paint);
    }

    public void setGradient(int sColor, int eColor){
        paint.setShader(new RadialGradient(radius, radius, radius-5,
                new int[]{sColor,eColor},
                new float[]{.6f,.95f},TileMode.CLAMP) );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //radius*2
        int desiredWidth = (int) radius*2;
        int desiredHeight = (int) radius*2;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //70dp exact
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST) {
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

}