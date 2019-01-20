package com.example.testTimeSelect.date;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.testTimeSelect.R;
import com.example.testTimeSelect.wheel.OnWheelChangedListener;
import com.example.testTimeSelect.wheel.WheelView;
import com.example.testTimeSelect.wheel.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author lipiao
 *         时间选择弹出框
 */
public class DialogSelectTime implements OnWheelChangedListener, View.OnClickListener {

    private AlertDialog dialog;
    private Activity activity;

    private WheelView mViewYear;
    private WheelView mViewMonth;

    private TextView selectDateSure;
    private TextView selectDateCancel;

    private Calendar calendar;

    private OnSelectDateDialog selectDateDialog;

    private String[] month;
    private String[] year;

    public DialogSelectTime(Activity activity, OnSelectDateDialog selectDateDialog) {
        this.activity = activity;
        this.selectDateDialog = selectDateDialog;
        calendar = Calendar.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void showTimeDialog() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_date_select, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialot_CitySelect);
        builder.setView(view);
        initDialogView(view);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth());
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * 初始化弹出
     *
     * @param view
     */
    public void initDialogView(View view) {
        mViewYear = (WheelView) view.findViewById(R.id.id_year);
        mViewMonth = (WheelView) view.findViewById(R.id.id_month);
        selectDateCancel = (TextView) view.findViewById(R.id.select_date_cancel);
        selectDateSure = (TextView) view.findViewById(R.id.select_date_sure);

        mViewYear.addChangingListener(this);
        mViewMonth.addChangingListener(this);

        mViewYear.setVisibleItems(7);
        mViewMonth.setVisibleItems(7);

        selectDateCancel.setOnClickListener(this);
        selectDateSure.setOnClickListener(this);
        year = initDate();
        mViewYear.setViewAdapter(new ArrayWheelAdapter<String>(activity, year));
        mViewYear.setCurrentItem(year.length - 7);
        updateMonth();
    }

    /**
     * 初始化时间-年
     */
    private String[] initDate() {
        int year = calendar.get(Calendar.YEAR);
        List<String> date_year = new ArrayList<String>();
        for (int i = year - 60; i <= year; i++) {
            date_year.add(i + "年");
        }
        return date_year.toArray(new String[date_year.size()]);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewYear) {
            updateMonth();
        } else if (wheel == mViewMonth) {

        }
    }

    /**
     * 月份选择
     */
    private void updateMonth() {
        int currentYear = mViewYear.getCurrentItem();
        int month_year = calendar.get(Calendar.MONTH) + 1;
        List<String> date_month = new ArrayList<String>();
        if (currentYear == 60) {
            for (int i = 1; i <= month_year; i++) {
                date_month.add(i + "月");
            }
        } else {
            for (int i = 1; i <= 12; i++) {
                date_month.add(i + "月");
            }
        }
        month = date_month.toArray(new String[date_month.size()]);
        mViewMonth.setViewAdapter(new ArrayWheelAdapter<String>(activity, month));
        mViewMonth.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_date_cancel:
                dialog.dismiss();
                break;
            case R.id.select_date_sure:
                selectDateDialog.OnSelectDate(year[mViewYear.getCurrentItem()] + " " + month[mViewMonth.getCurrentItem()]);
                dialog.dismiss();
                break;
        }
    }

}
