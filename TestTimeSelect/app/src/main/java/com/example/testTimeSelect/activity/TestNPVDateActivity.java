package com.example.testTimeSelect.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.testTimeSelect.R;
import com.example.testTimeSelect.TimePickerUrl;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class TestNPVDateActivity extends AppCompatActivity {
    private int year;
    private int month;
    private int day;
    private NumberPickerView picker_years;
    private NumberPickerView picker_month;
    private NumberPickerView picker_day;

    private String[] yearStr;
    private String[] monthStr;
    private String[] dayStr;
    private TimePickerUrl timePickerUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_npvdate);
        timePickerUrl = new TimePickerUrl(this);

        final Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date();
        mycalendar.setTime(mydate);
        year = mycalendar.get(Calendar.YEAR); // 获取Calendar对象中的年
        month = mycalendar.get(Calendar.MONTH) + 1;// 获取Calendar对象中的月
        day = mycalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天

        picker_years = (NumberPickerView) findViewById(R.id.picker_years);
        picker_month = (NumberPickerView) findViewById(R.id.picker_month);
        picker_day = (NumberPickerView) findViewById(R.id.picker_day);

        yearStr = getResources().getStringArray(R.array.years_display);
        monthStr = getResources().getStringArray(R.array.month_display);
        timePickerUrl.setData(picker_years, 0, yearStr.length - 1, timePickerUrl.getWeiZhi(year + "", yearStr));
        timePickerUrl.setData(picker_month, 0, monthStr.length - 1, timePickerUrl.getWeiZhi(month + "", monthStr));
        dayStr = timePickerUrl.changeDay(picker_day, year, month);
        timePickerUrl.setData(picker_day, 0, dayStr.length - 1, timePickerUrl.getWeiZhi(day + "", dayStr));
        //监听滚动 因为你前面的年月滚动会影响后面天数
        picker_years.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                year = Integer.parseInt(yearStr[newVal]);
                Log.e("年", oldVal + "+==" + newVal + "=====" + yearStr[newVal]);
                Log.e(year + "-" + month + "多少天", timePickerUrl.getNewDay(year, month) + "");
                timePickerUrl.changeDay(picker_day, year, month);
            }
        });
        picker_month.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                month = Integer.parseInt(monthStr[newVal]);
                Log.e("月", oldVal + "+==" + newVal + "=====" + monthStr[newVal]);
                Log.e(year + "-" + month + "多少天", timePickerUrl.getNewDay(year, month) + "====" + picker_day.getOneRecycleSize() + "==" + picker_day.getRawContentSize());
                timePickerUrl.changeDay(picker_day, year, month);
            }
        });
    }

}