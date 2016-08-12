package weijunfeng.com.androidutils.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import weijunfeng.com.androidutils.R;

/**
 * 悬浮窗体
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"></uses-permission>
 * 分析： 创建窗口主要注意一下几个参数
 * width：描述窗口的宽度，该变量是父类ViewGroup.LayoutParams的成员变量。
 * height：描述窗口的高度，该变量同样是父类ViewGroup.LayoutParams的成员变量。
 * x：描述窗口的起点X轴的坐标。
 * y：描述窗口起点Y轴的坐标。
 * type：窗口的类型，分为三个大类型：应用窗口，子窗口，系统窗口。
 * flag：窗口特征标记，比如是否全屏，是否隐藏标题栏等。
 * gravity：窗口的对齐方式，居中还是置顶或者置底等等
 */
public class ServiceFloatView extends Service {
    private WindowManager wm;
    private View floatView;
    private WindowManager.LayoutParams params;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showFloatWindow();
    }

    /**
     * 显示悬浮窗口
     */
    private void showFloatWindow() {
        wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();        //窗口类型为系统窗口
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;        //窗口起点位置
        params.x = 0;
        params.y = 0;        //窗口宽高
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;        //窗口对齐方式
        params.gravity = Gravity.CENTER;
        floatView = createFloatView();        //添加窗口
        wm.addView(floatView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();        //移除窗口
        wm.removeViewImmediate(floatView);
    }

    private View createFloatView() {
        FloatView floatView = new FloatView(this);
        Button button = new Button(this);
        button.setText("点我啊！");
        button.setTextColor(Color.BLACK);
        button.setBackgroundResource(R.drawable.image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServiceFloatView.this.getApplicationContext(), "Hi,我是悬浮窗口", Toast.LENGTH_SHORT).show();
            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        floatView.addView(button, params);
        return floatView;
    }

    /**
     * 重写dispatchTouch事件，使得窗口随着手指滑动而移动
     */
    private class FloatView extends FrameLayout {
        public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public FloatView(Context context) {
            super(context);
        }

        public FloatView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            float x, y;
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    x = ev.getX();
                    y = ev.getY();
                    params.x = (int) x;
                    params.y = (int) y;
                    wm.updateViewLayout(floatView, params);
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }
    }
}