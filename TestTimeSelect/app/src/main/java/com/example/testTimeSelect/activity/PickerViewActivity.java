package com.example.testTimeSelect.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.testTimeSelect.R;
import com.example.testTimeSelect.bean.AreaBean;
import com.example.testTimeSelect.bean.CityBean;
import com.example.testTimeSelect.city.CityDataUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_view);
    }

    public void onTimeClick(View view) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(PickerViewActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(PickerViewActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        }).build();

        pvTime.show();
    }

    private String getTime(Date date) {
        return date.toString();
    }

    private List<CityBean> options1Items;
    private List<AreaBean> options2Items;
    private List<String> options3Items;

    public void onOptionClick(View view) {
        options1Items = new ArrayList<>();
        options2Items = new ArrayList<>();
        options3Items = new ArrayList<>();

        CityDataUtil cityDataUtil = new CityDataUtil(this);
        options1Items.clear();
        options1Items.addAll(cityDataUtil.getProvinceList());
        options2Items.clear();
        options2Items.addAll(options1Items.get(0).getZones());

        //条件选择器
//        OptionsPickerView pvOptions = new OptionsPickerBuilder(PickerViewActivity.this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3 ,View v) {
//                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getAreaName()
//                        + options2Items.get(options2).getAreaName();
////                        + options3Items.get(options3);
//
//                Toast.makeText(PickerViewActivity.this, "tx:" + tx, Toast.LENGTH_SHORT).show();
//            }
//        }).build();
//        pvOptions.setPicker(options2Items, options1Items);
//        pvOptions.show();
    }
}
