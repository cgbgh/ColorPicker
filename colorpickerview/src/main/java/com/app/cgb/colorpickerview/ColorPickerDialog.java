package com.app.cgb.colorpickerview;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by cgb on 2017/8/17.
 */

public class ColorPickerDialog extends DialogFragment {

    private int curColor;
    private int nextColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.picker_dialog, null, false);
        ImageView curColorView = (ImageView) view.findViewById(R.id.cur_color);
        final ImageView nextColorView = (ImageView) view.findViewById(R.id.next_color);
        ColorPickerView colorPicker = (ColorPickerView) view.findViewById(R.id.color_picker);
        curColor = colorPicker.getCurrentColor();
        curColorView.setBackgroundColor(curColor);
        nextColorView.setBackgroundColor(curColor);
        colorPicker.setOnColorChanged(new OnColorChanged() {
            @Override
            public void onColorChanged(View view, int color) {
                nextColor = color;
                nextColorView.setBackgroundColor(color);
            }
        });
        nextColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curColor = nextColor;
                dismiss();
            }
        });
        return view;
    }
}
