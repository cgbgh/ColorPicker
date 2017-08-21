package com.app.cgb.colorpickerview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by cgb on 2017/8/17.
 */

public class ColorPickerView extends FrameLayout implements OnColorChanged{

    private static final int DEFAULT_BAR_SIZE = 60;
    private static final int DEFAULT_MARGIN = 20;
    private ColorPanel colorPanel;
    private AlphaBar alphaBar;
    private ColorBar colorBar;
    private OnColorChanged onColorChanged;

    public ColorPickerView(@NonNull Context context) {
        this(context,null);
    }

    public ColorPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorPickerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        colorPanel = new ColorPanel(context);
        colorBar = new ColorBar(context);
        alphaBar = new AlphaBar(context);
        colorPanel.setOnColorChanged(this);
        colorBar.setOnColorChanged(this);
        alphaBar.setOnColorChanged(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        int w = MeasureSpec.makeMeasureSpec(size, MeasureSpec.getMode(widthMeasureSpec));
        int h = MeasureSpec.makeMeasureSpec(size, MeasureSpec.getMode(heightMeasureSpec));
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (getChildCount() == 0) addComponent();
        super.onLayout(changed, left, top, right, bottom);
    }

    private void addComponent() {
        int size = getWidth() - getPaddingLeft() - getPaddingRight();
        int panelSize = size - DEFAULT_BAR_SIZE - DEFAULT_MARGIN;
        addView(colorPanel, new LayoutParams(panelSize, panelSize));
        LayoutParams colorBarParams = new LayoutParams(DEFAULT_BAR_SIZE, panelSize);
        colorBarParams.leftMargin = DEFAULT_MARGIN + panelSize;
        addView(colorBar, colorBarParams);

        LayoutParams alphaBarParams = new LayoutParams(size, DEFAULT_BAR_SIZE);
        alphaBarParams.topMargin = DEFAULT_MARGIN + panelSize;
        addView(alphaBar,alphaBarParams);
    }

    @Override
    public void onColorChanged(View view, int color) {
        if (view instanceof ColorBar){
            colorPanel.setCurrentColor(color);
        }else if (view instanceof ColorPanel){
            alphaBar.setCurrentColor(color);
        }else if (view instanceof AlphaBar){
            if (onColorChanged != null)
                onColorChanged.onColorChanged(this,alphaBar.getCurrentAlphaColor());
        }
    }

    public int getCurrentColor(){
        return alphaBar.getCurrentColor();
    }

    public void setOnColorChanged(OnColorChanged onColorChanged) {
        this.onColorChanged = onColorChanged;
    }
}
