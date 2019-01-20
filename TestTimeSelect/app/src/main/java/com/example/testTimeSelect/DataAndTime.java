package com.example.testTimeSelect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.testTimeSelect.activity.TanchaungActivity;

import java.util.Calendar;

public class DataAndTime extends AppCompatActivity implements DatePicker.OnDateChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_and_time);

        DatePicker datePicker = findViewById(R.id.dp_test);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataAndTime.this, TanchaungActivity.class));
            }
        });
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(DataAndTime.this, "您选择的日期是：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日!", Toast.LENGTH_SHORT).show();
    }
}
