package cn.demon.testdatabinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public final ObservableArrayList<Student> showDataList = new ObservableArrayList<>();

    {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            students.add(new Student("学生:" + i));
        }
        showDataList.clear();
        showDataList.addAll(students);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);
        binding.setActivity(this);

        UserAdapter userAdapter;
        binding.recyclerView.setAdapter(userAdapter = new UserAdapter(showDataList));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showDataList.addOnListChangedCallback(new DynamicChangeCallback(userAdapter));
    }

    public void addItem(View view) {
        if (showDataList.size() >= 3) {
            Student user = new Student("user_" + 100 + String.valueOf(new Random().nextInt() * 4));
            showDataList.add(1, user);
        }
    }

    public void addItemList(View view) {
        if (showDataList.size() >= 3) {
            List<Student> userList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Student user = new Student("user_" + 100 + String.valueOf(new Random().nextInt() * 4));
                userList.add(user);
            }
            showDataList.addAll(1, userList);
        }
    }

    public void removeItem(View view) {
        if (showDataList.size() >= 3) {
            showDataList.remove(1);
        }
    }

    public void updateItem(View view) {
        if (showDataList.size() >= 3) {
            Student user = showDataList.get(1);
            user.name = "user_" + new Random().nextInt();
            showDataList.set(1, user);
        }
    }
}
