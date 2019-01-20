package com.example.testTimeSelect;

import android.content.Context;

import java.util.Calendar;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * 时间的选择器帮助类
 * Created by Administrator on 2017/2/28.
 */

public class TimePickerUrl {
    private Context context;

    public TimePickerUrl(Context context) {
        this.context = context;
    }

    /**
     * 设置最大最小及其当前位置
     *
     * @param picker
     * @param minValue
     * @param maxValue
     * @param value
     */
    public void setData(NumberPickerView picker, int minValue, int maxValue, int value) {
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
    }

    /**
     * 获得当前月份多少天
     *
     * @param year
     * @param month
     * @return
     */
    public int getNewDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);//Java月份才0开始算
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }

    /**
     * 改变天数的数据源并返回
     *
     * @param picker_day
     * @param year
     * @param month
     * @return
     */
    public String[] changeDay(NumberPickerView picker_day, int year, int month) {
        String strDay[] = new String[0];
        switch (getNewDay(year, month)) {
            case 31:
                strDay = context.getResources().getStringArray(R.array.day_display);
                break;
            case 30:
                strDay = context.getResources().getStringArray(R.array.day_display30);
                break;
            case 29:
                strDay = context.getResources().getStringArray(R.array.day_display29);
                break;
            case 28:
                strDay = context.getResources().getStringArray(R.array.day_display28);
                break;
        }

        if (getNewDay(year, month) != picker_day.getRawContentSize()) {
            picker_day.refreshByNewDisplayedValues(strDay);
        }
        return strDay;
    }

    /***
     * 获得当前应显示的位置
     * @param str
     * @param strs
     * @return
     */
    public int getWeiZhi(String str, String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            if (str.equals(strs[i])) {
                return i;
            }
        }
        return 0;
    }

}