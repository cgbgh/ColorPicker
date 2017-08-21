package com.app.cgb.colorpickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

    private static final int DEFAULT_PICKER_RADIUS = 12;
    private int pickerRadius = DEFAULT_PICKER_RADIUS;
    private Paint greyBorderPaint;
    private Paint blackBorderPaint;
    private Paint insetPaint;
    private float cx;
    private float cy;
    private int pickedColor;
    private int redCur = 255;
    private int greenCur;
    private int blueCur;
    private OnColorChanged onColorChanged;
    private int borderWidth = 4;

    public ColorPanel(Context context) {
        this(context, null);
    }

    public ColorPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        greyBorderPaint = new Paint();
        blackBorderPaint = new Paint();
        insetPaint = new Paint();
        greyBorderPaint.setAntiAlias(true);
        greyBorderPaint.setStyle(Paint.Style.STROKE);
        greyBorderPaint.setStrokeWidth(borderWidth);
        greyBorderPaint.setColor(Color.GRAY);
        blackBorderPaint.setAntiAlias(true);
        blackBorderPaint.setStyle(Paint.Style.STROKE);
        blackBorderPaint.setStrokeWidth(borderWidth);
        blackBorderPaint.setColor(Color.WHITE);
        insetPaint.setAntiAlias(true);
        insetPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        cx = Math.max(0,Math.min(x,getWidth()));
        cy = Math.max(0,Math.min(y,getHeight()));
        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        int w = MeasureSpec.makeMeasureSpec(size, MeasureSpec.getMode(widthMeasureSpec));
        int h = MeasureSpec.makeMeasureSpec(size, MeasureSpec.getMode(heightMeasureSpec));
        cx = size;
        setMeasuredDimension(w, h);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawPanel(canvas);
        drawPicker(canvas);

        if (onColorChanged != null)
            onColorChanged.onColorChanged(this,getPickerColorByPosition(cx,cy));
    }

    private void drawPanel(Canvas canvas) {
        RectF rectF = new RectF(0, 0, getWidth(), getWidth());
        Paint paint = new Paint();
        LinearGradient valShader = new LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, Color.WHITE, Color.BLACK, Shader.TileMode.MIRROR);
        LinearGradient satShader = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.top, Color.WHITE, Color.rgb(redCur,greenCur,blueCur), Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(satShader, valShader, new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        paint.setShader(composeShader);
        canvas.drawRect(rectF, paint);
    }

    private void drawPicker(Canvas canvas) {
        canvas.drawCircle(cx, cy, pickerRadius + borderWidth, greyBorderPaint);
        canvas.drawCircle(cx, cy, pickerRadius, blackBorderPaint);
    }

    public int getPickerColorByPosition(float x, float y) {
        float hRatio = 1 - y / getWidth();
        float wRatio = 1 - x / getWidth();
        int red = (int) ((hRatio * 255 - hRatio * redCur) * wRatio + hRatio * redCur);
        int green = (int) ((hRatio * 255 - hRatio * greenCur) * wRatio + hRatio * greenCur);
        int blue = (int) ((hRatio * 255 - hRatio * blueCur) * wRatio + hRatio * blueCur);
        int rgb = Color.rgb(red, green, blue);
        return rgb;
    }


    public int getPickedColor() {
        return pickedColor;
    }

    public void setCurrentColor(int curColor){
        redCur = (curColor >> 16) & 0xFF;
        greenCur = (curColor >> 8) & 0xFF;
        blueCur = curColor & 0xFF;
        invalidate();
    }

    public void setOnColorChanged(OnColorChanged onColorChanged){
        this.onColorChanged = onColorChanged;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onColorChanged != null)
            onColorChanged = null;
    }
}
