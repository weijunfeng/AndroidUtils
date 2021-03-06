package weijunfeng.com.androidutils.view;


import android.content.Context;
import android.graphics.*;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import weijunfeng.com.androidutils.L;

/**
 * 快速索引
 * <p>
 * 用于根据字母快速定位联系人
 */
public class QuickIndexBar extends View {
    private Context mcontext;
    private static final String[] LETTERS = {
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"};

    private static final String TAG = QuickIndexBar.class.getSimpleName();
    private int touchIndex = -1;
    private Paint mPaint;
    private int cellWidth;
    private float cellHeight;
    private OnLetterClickListener listener;
    private Rect letterBounds;
    private GestureDetector gestureDetector;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mcontext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        // 获取文本的高度
        // 矩形
        letterBounds = new Rect();
        gestureDetector = new GestureDetector(mcontext, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                L.i(TAG, "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                L.i(TAG, "onShowPress");

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                L.i(TAG, "onSingleTapUp");

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                L.i(TAG, "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                L.i(TAG, "onLongPress");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                L.i(TAG, "onFling");
                return false;
            }
        });
    }

    public OnLetterClickListener getListener() {
        return listener;
    }

    /**
     * 设置字母更新监听
     *
     * @param listener
     */
    public void setListener(OnLetterClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < LETTERS.length; i++) {
            String text = LETTERS[i];
            // 计算坐标
            int x = (int) (cellWidth / 2.0f - mPaint.measureText(text) / 2.0f);
            mPaint.getTextBounds(text, 0, text.length(), letterBounds);
            int textHeight = letterBounds.height();
            int y = (int) (cellHeight / 2.0f + textHeight / 2.0f + i * cellHeight);

            // 根据按下的字母, 设置画笔颜色
            mPaint.setColor(touchIndex == i ? Color.GRAY : Color.WHITE);

            // 绘制文本A-Z
            canvas.drawText(text, x, y, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = -1;
//        getParent().requestDisallowInterceptTouchEvent(true);//请求父类不拦截事件
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                L.i(TAG, "ontouch down");
                // 获取当前触摸到的字母索引
                index = (int) (event.getY() / cellHeight);
                if (index >= 0 && index < LETTERS.length) {
                    // 判断是否跟上一次触摸到的一样
                    if (index != touchIndex) {
                        if (listener != null) {
                            listener.onLetterUpdate(LETTERS[index]);
                        }
                        Log.d(TAG, "onTouchEvent: " + LETTERS[index]);

                        touchIndex = index;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                L.i(TAG, "ontouch move");
                break;
            case MotionEvent.ACTION_UP:
                L.i(TAG, "ontouch up");
                if (touchIndex != -1) {
                    touchIndex = -1;
                    invalidate();
                }
                break;

            default:
                break;
        }

//        gestureDetector.onTouchEvent(event);
        return false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取单元格的宽和高

        cellWidth = getMeasuredWidth();

        int mHeight = getMeasuredHeight();
        cellHeight = mHeight * 1.0f / LETTERS.length;

    }

    /**
     * 暴露一个字母的监听
     */
    public interface OnLetterClickListener {
        void onLetterUpdate(String letter);
    }


}
