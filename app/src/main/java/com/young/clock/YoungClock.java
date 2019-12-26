package com.young.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class YoungClock extends View {
    private Paint circlePaint,dialPaint,numberPaint;
    private float mWidth,mHeight;
    private float radius;
    private float circleX,circleY;
    private int minute,second;
    private double hour;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    invalidate();
                    break;
            }
        }
    };

    public YoungClock(Context context) {
        super(context);
        initPaint();
    }

    public YoungClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public YoungClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(10);

        dialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dialPaint.setColor(Color.BLUE);
        dialPaint.setStyle(Paint.Style.STROKE);
        dialPaint.setStrokeWidth(5);

        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(Color.BLUE);
        numberPaint.setStrokeWidth(5);
        numberPaint.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if(mWidth<=mHeight){
            radius = mWidth/2-10;
            circleX = mWidth/2;
            circleY = mHeight/2;
        }else{
            radius = mWidth/2-10;
            circleX = mWidth/2;
            circleY = mHeight/2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTime();
        drawCirclePoint(canvas);
        drawCircle(canvas);
        drawDial(canvas);
        drawPoint(canvas);
    }

    private void drawPoint(Canvas canvas) {
        canvas.translate(circleX,circleY);
        float hourX = (float) (Math.cos(Math.toRadians(hour*30+270))*radius*0.5f);
        float hourY = (float) (Math.sin(Math.toRadians(hour*30+270))*radius*0.5f);
        float minuteX = (float) (Math.cos(Math.toRadians(minute*6+270))*radius*0.8f);
        float minuteY = (float) (Math.sin(Math.toRadians(minute*6+270))*radius*0.8f);
        float secondX = (float) (Math.cos(Math.toRadians(second*6+270))*radius*0.8f);
        float secondY = (float) (Math.sin(Math.toRadians(second*6+270))*radius*0.8f);
        canvas.drawLine(0,0,hourX,hourY,circlePaint);
        canvas.drawLine(0,0,minuteX,minuteY,circlePaint);
        canvas.drawLine(0,0,secondX,secondY,dialPaint);
        handler.sendEmptyMessageDelayed(0,1000);

    }

    private void drawDial(Canvas canvas) {
        Point startPoint1 = new Point(circleX,circleY-radius);
        Point endPoint1 = new Point(circleX,circleY-radius+40);
        Point startPoint2 = new Point(circleX,circleY-radius);
        Point endPoint2 = new Point(circleX,circleY-radius+10);
        String clockNumber;
        for(int i=0;i<60;i++){
            if(i%5==0){
                if(i==0){
                    clockNumber = "12";
                }else{
                    clockNumber = String.valueOf(i/5);
                }
                canvas.drawLine(startPoint1.getX(),startPoint1.getY(),
                        endPoint1.getX(),endPoint1.getY(),circlePaint);
                canvas.drawText(clockNumber,circleX-numberPaint.measureText(clockNumber)/2,endPoint1.getY()+30,numberPaint);
                Log.e("young","clockNumber: "+clockNumber);
            }else{
                canvas.drawLine(startPoint2.getX(),startPoint2.getY(),endPoint2.getX(),endPoint2.getY(),circlePaint);
            }
            canvas.rotate(360/60,circleX,circleY);
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(circleX,circleY,radius,circlePaint);
    }

    private void drawCirclePoint(Canvas canvas) {
        canvas.drawCircle(circleX,circleY,5,circlePaint);
    }

    private void setTime() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        second = calendar.get(Calendar.SECOND);
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR)+minute/12*0.2;
    }

    public void startClock(){
        setTime();
        invalidate();
    }

    public void stopClock(){
        handler.removeMessages(0);
    }
}
