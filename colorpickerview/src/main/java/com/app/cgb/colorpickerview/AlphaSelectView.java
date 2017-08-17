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

public class AlphaSelectView extends View {

    private int curColor = Color.RED;
    private Paint paint;
    private Paint selectorPaint;
    private float border = 4f;
    private int curRed;
    private int curGreen;
    private int curBlue;
    private float selectorWidth = 8f;
    private float curX;
    private Paint borderPaint;

    public AlphaSelectView(Context context) {
        this(context, null);
    }

    public AlphaSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
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

    public void setCurrentColor(int color) {
        curColor = color;
        curRed = (color >> 16) & 0xFF;
        curBlue = (color >> 8) & 0xFF;
        curGreen = color & 0xFF;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPane(canvas);
        drawBorder(canvas);
        drawSelector(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        curX = Math.max(0, Math.min(x, getWidth() - selectorWidth));
        invalidate();
        return true;
    }


    private void drawPane(Canvas canvas) {
        RectF rect = new RectF(border/2, border/2, getWidth() - border/2, getHeight() - border/2);
        LinearGradient gradient = new LinearGradient(
                rect.left, rect.top, rect.right, rect.top,
                new int[]{curColor, Color.argb(0, curRed, curGreen, curBlue)},
                null, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawRect(rect, paint);
    }

    private void drawBorder(Canvas canvas){
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect,borderPaint);
    }

    private void drawSelector(Canvas canvas) {
        RectF rect = new RectF(curX, 0, selectorWidth + curX, getHeight());
        canvas.drawRect(rect, selectorPaint);
    }
}
