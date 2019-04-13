package cn.demon.testdatabinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.demon.testdatabinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final User user = new User("Demon", "123");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setUserInfoHandler(new UserInfoHandler());

        binding.setUserInfo(user);
    }

    /**
     * 必须是公有的
     */
    public class UserInfoHandler {

        public void changeNameAndPsw() {
            user.setName("Demons96");
            user.setPassword("666");
        }

        public void changePsw() {
            user.setPassword("995415981");
        }

        public void startListAct() {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
        }

        public void startRecyclerViewAct() {
            startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
        }
    }
}
