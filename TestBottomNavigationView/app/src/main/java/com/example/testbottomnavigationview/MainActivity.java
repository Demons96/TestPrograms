package com.example.testbottomnavigationview;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 教程地址
 * https://www.jianshu.com/p/8b7186624ea5
 *
 * @author yunPeng.Gong
 * @date 19.2.19
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        mRecyclerView = findViewById(R.id.rv_list);
        initRecyclerView();
    }

    private void initRecyclerView() {
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }

        setLayoutManager();
        setAdapter();
        setDrawable();
        setItemAnimator();
        setItemTouchHelper();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 这里通过两个菜单按钮来观看我们的动画效果
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                mAdapter.addData(0);
                mRecyclerView.scrollToPosition(0);
                break;
            case R.id.action_remove:
                mAdapter.removeData(0);
                mRecyclerView.scrollToPosition(0);
                break;
        }
        return true;
    }

    /**
     * 设置样式
     */
    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        layoutManager = new GridLayoutManager(this, 2);
//        layoutManager = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mAdapter = new MainAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header, mRecyclerView, false));
//        mAdapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer, mRecyclerView, false));
    }

    /**
     * 设置分割线
     */
    private void setDrawable() {
        DividerItemDecoration mDivider;//分隔线
        //初始化分隔线、添加分隔线
        mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        // 可单独设置分割线
        mDivider.setDrawable(getResources().getDrawable(R.drawable.my_divider));
        mRecyclerView.addItemDecoration(mDivider);

//        MyGridItemDecoration gridItemDecoration =  new MyGridItemDecoration(this);
//        mRecyclerView.addItemDecoration(gridItemDecoration);
    }

    /**
     * 添加动画
     */
    private void setItemAnimator() {
        /**
         * 既然是动画，就会有时间，我们把动画执行时间变大一点来看一看效果
         */
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(300);
        defaultItemAnimator.setRemoveDuration(300);
        mRecyclerView.setItemAnimator(defaultItemAnimator);
    }

    /**
     * 滑动删除和拖拽工具类
     */
    private void setItemTouchHelper() {
        ItemTouchHelper.SimpleCallback mCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                ItemTouchHelper.START | ItemTouchHelper.END) {
//            /**
//             * 指定可以拖拽（drag）和滑动（swipe）的方向
//             * ItemTouchHelper.UP、ItemTouchHelper.DOWN
//             * ItemTouchHelper.LEFT、ItemTouchHelper.RIGHT
//             * ItemTouchHelper.START、ItemTouchHelper.END
//             * 若返回0，表示不触发滑动or拖拽
//             */
//            @Override
//            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//                return 0;
//            }

            /**
             * 拖拽到新位置时候的回调方法
             * @param viewHolder  拖动的ViewHolder
             * @param viewHolder1 目标位置的ViewHolder
             */
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = viewHolder1.getAdapterPosition();//得到目标ViewHolder的position

                //使用集合工具类Collections，分别把中间所有的item的位置重新交换
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDatas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mDatas, i, i - 1);
                    }
                }
                //通知Adapter更新状态
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            /**
             * 滑动时的回调方法
             * @param viewHolder 滑动的ViewHolder
             * @param i          滑动的方向
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                mDatas.remove(position);//侧滑删除数据
                mAdapter.notifyItemRemoved(position); //通知Adapter更新状态
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }

            /**
             * 是否支持长按拖拽，默认为true，表示支持长按拖拽
             *                        对应长按移动位置功能
             *   也可以返回false，手动调用startDrag()方法启动拖拽
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }

            /**
             * 是否支持任意位置触摸事件发生时启用滑动操作，默认为true，表示支持滑动
             *                        对应滑动删除功能
             *   也可以返回false，手动调用startSwipe()方法启动滑动
             */
            @Override
            public boolean isItemViewSwipeEnabled() {
                return super.isItemViewSwipeEnabled();
            }
        };
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(mCallback);
        itemTouchHelper1.attachToRecyclerView(mRecyclerView);
    }

    /**
     * @param view 就是我们点击的itemView
     */
    public void myItemClick(View view) {
        // 获取itemView的位置
        int position = mRecyclerView.getChildAdapterPosition(view);
        Toast.makeText(MainActivity.this, "点击了 " + mDatas.get(position), Toast.LENGTH_SHORT).show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.item_1:
                            //do something
//                        Toast.makeText(MainActivity.this, "item_1", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.item_2:
                            //do something
//                        Toast.makeText(MainActivity.this, "item_2", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.item_3:
                            //do something
//                        Toast.makeText(MainActivity.this, "item_3", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.item_4:
                            //do something
//                        Toast.makeText(MainActivity.this, "item_4", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                }
            };

}
