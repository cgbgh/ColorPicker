package com.app.cgb.colorpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.cgb.colorpickerview.AlphaBar;
import com.app.cgb.colorpickerview.ColorBar;
import com.app.cgb.colorpickerview.ColorPanel;
import com.app.cgb.colorpickerview.ColorPickerDialog;
import com.app.cgb.colorpickerview.OnColorChanged;

public class MainActivity extends AppCompatActivity implements OnColorChanged, View.OnClickListener {

//    private ColorBar panel;
//    private ColorPanel colorPicker;
//    private AlphaBar asv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        panel = (ColorBar) findViewById(R.id.color_panel);
//        colorPicker = (ColorPanel) findViewById(R.id.color_picker);
//        panel.setOnColorChanged(this);
//        asv = (AlphaBar) findViewById(R.id.asv);
//        colorPicker.setOnColorChanged(this);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onColorChanged(View view, int color) {
//        switch (view.getId()){
//            case R.id.color_panel:
//                colorPicker.setCurrentColor(color);
//                break;
//            case R.id.color_picker:
//                asv.setCurrentColor(color);
//        }
    }

    @Override
    public void onClick(View v) {
        ColorPickerDialog dialog = new ColorPickerDialog();
        dialog.show(getFragmentManager(),"tag");
    }
}
