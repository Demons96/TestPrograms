package cn.demon.testdatabinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.demon.testdatabinding.databinding.ActivityRecyclerViewBinding;

/**
 * DataBanding 实现 RecyclerView
 *
 * @author Demons96
 * create at 19.4.13 18:01
 */
public class RecyclerViewActivity extends AppCompatActivity {
    /**
     * 要展示的数据源
     */
    public final ObservableArrayList<Student> showDatas = new ObservableArrayList<>();

    {
        // 初始化数据源
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            students.add(new Student("学生:" + i));
        }
        showDatas.clear();
        showDatas.addAll(students);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);
        binding.setActivity(this);
    }

    public void onBindItem(ViewDataBinding binding, final Object data, int position) {
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 数据的实体类
    public class Student {
        public String name;

        public Student(String name) {
            this.name = name;
        }
    }
}
