package com.example.testTimeSelect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

// 已经实现单行的数字显示
public class TestNumberPickerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_number_picker_view);

        NumberPickerView picker = findViewById(R.id.picker);
        String[] display = new String[150];
        for (int i = 90; i < 240; i++) {
            display[i - 90] = i + "";
        }
        picker.refreshByNewDisplayedValues(display);

    }
}
