package com.example.administrator.registerui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class Togglebutton extends View {
    private Region regionClip,regionClick;//点击的截取区域
    private Path pathRoundOut,pathCircleOut,pathCircleIn,pathRoundIn_normal,getPathRoundIn_select;//路径
    private Paint paintRoundOut,paintRoundInNormal,paintRoundInSelect,paintCircleIn,paintCircleOut;//画笔
    private float varySet;//动画的变化值
    private float roundOutWidth=120f,roundOutHeight=60f,circleRadio=28f;//控件的参数
    private int mHeight,mWidth;//画布的宽高
    private boolean choice=false,inner=false;//用来判断事件的句柄
    private ValueAnimator animatorOpen,animatorClose;//动画
    private Matrix matrix;//矩阵
    private float[] dst=new float[2];//当前点击的点
    public Togglebutton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initValueAnimator();
        matrix=new Matrix();
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        paintCircleIn=new Paint();
        paintCircleIn.setStyle(Paint.Style.FILL);
        paintCircleIn.setColor(Color.WHITE);
        paintCircleIn.setAntiAlias(true);

        paintCircleOut=new Paint();
        paintCircleOut.setAntiAlias(true);
        paintCircleOut.setStyle(Paint.Style.STROKE);
        paintCircleOut.setStrokeWidth(1);
        paintCircleOut.setColor(Color.parseColor("#8a8a8a"));

        paintRoundOut=new Paint();
        paintRoundOut.setColor(Color.parseColor("#8a8a8a"));
        paintRoundOut.setAntiAlias(true);
        paintCircleOut.setStyle(Paint.Style.STROKE);
        paintCircleOut.setAntiAlias(true);
        paintCircleOut.setStrokeWidth(1);

        paintRoundInNormal=new Paint();
        paintRoundInNormal.setAntiAlias(true);
        paintRoundInNormal.setStyle(Paint.Style.FILL);
        paintRoundInNormal.setColor(Color.WHITE);

        paintRoundInSelect=new Paint();
        paintRoundInSelect.setStyle(Paint.Style.FILL);
        paintRoundInSelect.setAntiAlias(true);
        paintRoundInSelect.setColor(Color.BLUE);
    }

    /**
     * 初始化路径
     */
    private void initPath() {
        pathCircleIn=new Path();
        pathCircleOut=new Path();
        pathRoundOut=new Path();
        pathRoundIn_normal=new Path();
        getPathRoundIn_select=new Path();
        pathRoundOut.addRoundRect(new RectF(-roundOutWidth/2,-roundOutHeight/2,roundOutWidth/2,roundOutHeight/2),circleRadio+1,circleRadio+1, Path.Direction.CW);
        pathRoundIn_normal.addRoundRect(new RectF(-roundOutWidth / 2 + varySet, -roundOutHeight / 2 + varySet / 2, roundOutWidth / 2 - varySet, roundOutHeight / 2 - varySet / 2), circleRadio, circleRadio, Path.Direction.CW);
        pathCircleIn.addCircle(-roundOutWidth/4+varySet,0,circleRadio, Path.Direction.CW);
        pathCircleOut.addCircle(-roundOutWidth/4+varySet,0,circleRadio+1, Path.Direction.CW);
        getPathRoundIn_select.addRoundRect(new RectF(-roundOutWidth/2,-roundOutHeight/2,roundOutWidth/2,roundOutHeight/2),circleRadio,circleRadio, Path.Direction.CW);
        regionClick=new Region();
        regionClick.setPath(pathRoundOut,regionClip);
    }

    /**
     * 初始化动画
     */
    private void initValueAnimator() {
        animatorOpen = ValueAnimator.ofFloat(0,roundOutWidth/2);
        animatorOpen.setRepeatCount(0);
        animatorOpen.setDuration(500);
        animatorOpen.setInterpolator(new LinearInterpolator());
        animatorOpen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                varySet= (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animatorClose = ValueAnimator.ofFloat(roundOutWidth/2,0);
        animatorClose.setRepeatCount(0);
        animatorClose.setDuration(500);
        animatorClose.setInterpolator(new LinearInterpolator());
        animatorClose.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                varySet= (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight=h;
        mWidth=w;
        //获取手势的剪裁区域
        regionClip=new Region(-mWidth,-mHeight,mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布平移
        canvas.translate(mWidth/2,mHeight/2);
        initPath();
        matrix.reset();
        if(matrix.isIdentity()){
            //由于画布平移了，矩阵要相应变换，对区域的判断坐标会出问题
            canvas.getMatrix().invert(matrix);
        }
        drawPath(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                dst[0]=event.getRawX();
                dst[1]=event.getRawY();
                matrix.mapPoints(dst);
                //判断手势是否在画的圆角区域内
                inner=regionClick.contains((int)dst[0],(int) dst[1]);
                break;
            case MotionEvent.ACTION_UP:
                dst[0]=event.getRawX();
                dst[1]=event.getRawY();
                matrix.mapPoints(dst);
                //判断手势是否离开
                if(inner&&regionClick.contains((int)dst[0],(int) dst[1])){
                    if(animatorOpen.isRunning()){
                        animatorOpen.cancel();
                    }
                    if (animatorClose.isRunning()){
                        animatorClose.cancel();
                    }
                    choice=!choice;
                    if(choice){
                        animatorOpen.start();
                    }else {
                        animatorClose.start();
                    }
                }

                break;
            default:break;
        }
        return true;
    }

    /**
     * 画
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        if(choice){
            canvas.drawPath(pathRoundOut,paintRoundInSelect);
        }else {
            canvas.drawPath(pathRoundOut,paintRoundOut);
        }
        canvas.drawPath(getPathRoundIn_select,paintRoundInSelect);
        canvas.drawPath(pathRoundIn_normal,paintRoundInNormal);
        canvas.drawPath(pathCircleOut,paintCircleOut);
        canvas.drawPath(pathCircleIn,paintCircleIn);
    }
}