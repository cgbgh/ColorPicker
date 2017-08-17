package com.app.cgb.colorpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.cgb.colorpickerview.AlphaSelectView;
import com.app.cgb.colorpickerview.ColorPanel;
import com.app.cgb.colorpickerview.ColorPickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ColorPanel panel = (ColorPanel) findViewById(R.id.color_panel);
        final ColorPickerView colorPicker = (ColorPickerView) findViewById(R.id.color_picker);
        panel.setOnSelectChanged(new ColorPanel.OnSelectChanged() {
            @Override
            public void onSelectChanged(float y) {
                int currentColor = panel.getCurrentColor();
                colorPicker.setCurrentColor(currentColor);
            }
        });
        final AlphaSelectView asv = (AlphaSelectView) findViewById(R.id.asv);
        colorPicker.setOnSelectChanged(new ColorPickerView.OnSelectChanged() {
            @Override
            public void onSelectChanged(float x, float y) {
                int color = colorPicker.getPickerColorByPosition(x, y);
                asv.setCurrentColor(color);
            }
        });
    }
}
