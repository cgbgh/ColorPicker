package com.app.cgb.colorpickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cgb on 2017/8/16.
 */

public class ColorPanel extends View {

    private int[] colorArray;
    private Paint paint;
    private Paint borderPaint;
    private Paint selectorPaint;
    private float curY;
    private int selectorHeight = 8;
    private int border = 4;
    private OnSelectChanged onSelectChanged;

    public ColorPanel(Context context) {
        this(context,null);
    }

    public ColorPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        colorArray = new int[]{
                0xffff0000, 0xffffff00, 0xff00ff00, 0xff00ffff,
                0xff0000ff, 0xffff00ff, 0xffff0000
        };
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(border);
        borderPaint.setColor(Color.BLACK);

        selectorPaint = new Paint();
        selectorPaint.setAntiAlias(true);
        selectorPaint.setStyle(Paint.Style.STROKE);
        selectorPaint.setStrokeWidth(border);
        selectorPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPane(canvas);
        drawBorder(canvas);
        drawSelector(canvas);
        if (onSelectChanged != null) onSelectChanged.onSelectChanged(curY);
    }

    private void drawSelector(Canvas canvas){
        RectF rectF = new RectF(0, curY, getWidth(), curY + selectorHeight);
        canvas.drawRect(rectF,selectorPaint);
    }

    private void drawBorder(Canvas canvas){
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect,borderPaint);
    }

    private void drawPane(Canvas canvas) {
        RectF rectF = new RectF(border/2, border/2, getWidth() - border/2, getHeight() - border/2);
        LinearGradient linearGradient = new LinearGradient(rectF.left, rectF.top,
                rectF.left, rectF.bottom, colorArray, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(rectF,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        curY = Math.max(0,Math.min(y,getHeight() - selectorHeight));
        invalidate();
        return true;
    }

    private int getSelectColor(float y) {
        int height = getHeight();
        int distance = height / 6;
        if (y <= 0) y = 0;
        else if (y >= height) y = height;

//        int red = 0, green = 0, blue = 0;
        int[] rgb = new int[3];
//        第几部分
        int part = (int) (y / distance);
//        相对于当前部分的距离
        float curDis = y - part * distance;
//        相对于当前部分的比例
        float ratio = curDis / distance;
//        获取当前部分对应的值，偶数部分增长，奇数部分递减
        int curVal = (int) Math.abs((part % 2) * 255 - ratio * 255);
//   part     R       G       B
//    0      255    curVal    0
//    1     curVal   255      0
//    2       0      255    curVal
//    3       0     curval   255
//    4     curval    0      255
//    5      255      0     curVal
        rgb[(7-part) % 3] = curVal;
        rgb[(part + 1) / 2 % 3] = 255;
//        if (y <= distance){
//            red = 255;
//            green = (int) (255 * y/distance);
//        }else  if (y <= distance * 2){
//            red = (int) (255 * (1 - (y - distance)/distance));
//            green = 255;
//        }else if (y <= distance * 3){
//            green = 255;
//            blue = (int) (255 * (y - 2 * distance)/distance);
//        }else if (y <= distance * 4){
//            blue = 255;
//            green = (int) (255 * (1 - (y - distance * 3)/distance));
//        }else if (y <= distance * 5){
//            blue = 255;
//            red = (int) (255 * (y - distance * 4) / distance);
//        }else {
//            red = 255;
//            blue = (int) (255 * (1 - (y - distance * 5)/distance));
//        }
        return Color.rgb(rgb[0],rgb[1],rgb[2]);
//        return Color.rgb(red,green,blue);
    }

    public int getCurrentColor(){
        return getSelectColor(curY);
    }

    public void setOnSelectChanged(OnSelectChanged onSelectChanged){
        this.onSelectChanged = onSelectChanged;
    }

    public interface OnSelectChanged{
        void onSelectChanged(float y);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onSelectChanged != null) onSelectChanged = null;
    }
}
