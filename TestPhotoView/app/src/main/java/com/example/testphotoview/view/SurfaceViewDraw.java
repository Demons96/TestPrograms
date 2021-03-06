package com.example.testphotoview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.testphotoview.R;

/**
 * 使用 SurfaceView 绘制图片
 */
public class SurfaceViewDraw extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Rect aa;
    private Bitmap bitmap;

    public SurfaceViewDraw(Context context) {
        this(context, null);
    }

    public SurfaceViewDraw(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //得到控制器
        surfaceHolder = getHolder();
        //对surfaceview进行操作
        surfaceHolder.addCallback(this);

        paint = new Paint();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //重要的一点要说明.在这里可以在线程里面用canvas绘制图片,所以为什么SurfaceView比较时候绘制图片和图形
        Canvas canvas = surfaceHolder.lockCanvas();
        //开始画
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        //解锁画布
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //surface 大小发生变化的时候
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //销毁时发生,一般在这里将画图停止.释放
    }
}
