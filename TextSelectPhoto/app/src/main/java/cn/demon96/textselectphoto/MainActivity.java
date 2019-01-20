package cn.demon96.textselectphoto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Matisse.from(MainActivity.this)
                .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                .showSingleMediaType(true)  // 显示单一的媒体类型
                .theme(R.style.Matisse_Zhihu)   // 亮蓝色主题
                .maxSelectable(9) // 图片选择的最多数量
                .countable(true)    // 缩略图上的数字
                .imageEngine(new Glide4Engine()) // 使用的图片加载引擎
                .thumbnailScale(0.85f) // 缩略图的比例
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

}
