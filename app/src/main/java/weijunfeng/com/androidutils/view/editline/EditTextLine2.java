package weijunfeng.com.androidutils.view.editline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class EditTextLine2 extends View {
    boolean fouced = false;
    private Paint mPaint = new Paint();
    private RectF drawRect = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (drawRect == null) {
                return;
            }
            int progressBarLeft = (int) drawRect.left;
            int progressBarRight = (int) drawRect.right;
            int mid = getMeasuredWidth() / 2;
            if (fouced) {
                if (progressBarLeft == 0) {
                    return;
                }
                if (progressBarLeft == -1) {
                    progressBarLeft = progressBarRight = mid;
                }
                progressBarLeft = progressBarLeft - 20;
                progressBarRight = progressBarRight + 20;
                if (progressBarLeft <= 0) {
                    progressBarLeft = 0;
                }
                if (progressBarRight >= getMeasuredWidth()) {
                    progressBarRight = getMeasuredWidth();
                }
            } else {
                if (progressBarLeft == -1) {
                    return;
                }
                progressBarLeft = progressBarLeft + 20;
                progressBarRight = progressBarRight - 20;
                if (progressBarLeft >= mid) {
                    progressBarLeft = -1;
                }
                if (progressBarRight <= mid) {
                    progressBarRight = -1;
                }
            }
            System.out.println("progressBarLeft = " + progressBarLeft);
            System.out.println("progressBarRight = " + progressBarRight);
            drawRect.left = progressBarLeft;
            drawRect.right = progressBarRight;
            invalidate();
            if (drawRect.left > 0 && drawRect.left < mid) {
                mHandler.sendEmptyMessageDelayed(0, 10);
            }
        }
    };

    public EditTextLine2(Context context) {
        this(context, null);
    }

    public EditTextLine2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextLine2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (drawRect == null) {
            drawRect = new RectF(-1, 0, -1, 10);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        mHandler.removeCallbacksAndMessages(null);
        fouced = selected;
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(drawRect, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        drawRect = null;
    }
}