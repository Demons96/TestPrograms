package com.example.testTimeSelect.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testTimeSelect.R;
import com.example.testTimeSelect.city.DialogSelectAddress;
import com.example.testTimeSelect.city.OnSelectCityDialog;
import com.example.testTimeSelect.date.DialogSelectTime;
import com.example.testTimeSelect.date.OnSelectDateDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnSelectDateDialog, OnSelectCityDialog {
    private TextView selectCity;
    private TextView selectDate;

    private DialogSelectTime timeDialog;
    private DialogSelectAddress cityDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cityBtn = findViewById(R.id.city);
        Button dateBtn = findViewById(R.id.date);
        selectCity = findViewById(R.id.select_city);
        selectDate = findViewById(R.id.select_date);
        cityBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        timeDialog = new DialogSelectTime(MainActivity.this, this);
        cityDialog = new DialogSelectAddress(MainActivity.this, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city:
                cityDialog.showDialog();
                break;
            case R.id.date:
                timeDialog.showTimeDialog();
                break;
        }
    }

    @Override
    public void OnSelectDate(String selected) {
        selectDate.setText(selected);
    }

    @Override
    public void onSelectCity(String selectedCity, String cityCode, String pidCode) {
        selectCity.setText(selectedCity + "  省编码：" + cityCode + "城市编码：" + pidCode);
    }
}
