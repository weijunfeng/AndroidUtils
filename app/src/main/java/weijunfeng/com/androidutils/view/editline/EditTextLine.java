package weijunfeng.com.androidutils.view.editline;//package com.weijunfeng.customview;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Handler;
//import android.os.Message;
//import android.os.SystemClock;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//
//import com.weijunfeng.customview.DensityUtil;
//
//public class EditTextLine extends View {
//    private static ThreadLocal<Integer> local = new ThreadLocal() {
//        protected Integer initialValue() {
//            return Integer.valueOf(0);
//        }
//    };
//    private volatile boolean focused = false;
//    Handler handler = new Handler() {
//        public void handleMessage(Message paramMessage) {
//            super.handleMessage(paramMessage);
//            EditTextLine localEditTextLine = (EditTextLine) paramMessage.obj;
//            EditTextLine.this.setProgressValue(localEditTextLine, paramMessage);
//        }
//    };
//    private volatile boolean isFinished = false;
//    private int mLeftLimit = 0;
//    private int mLeftValue;
//    private int mMaxValue = 100;
//    private int mProgressBarGrayHeight;
//    private int mProgressBarHeight;
//    private int mRightLimit = 1000;
//    private int mRightValue = this.mMaxValue;
//    private Paint paint = new Paint();
//    private int proPaddingLeftAndRight = 0;
//
//    public EditTextLine(Context paramContext) {
//        this(paramContext, null);
//    }
//
//    public EditTextLine(Context paramContext, AttributeSet paramAttributeSet) {
//        this(paramContext, paramAttributeSet, 0);
//    }
//
//    public EditTextLine(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
//        super(paramContext, paramAttributeSet, paramInt);
//        setBackgroundDrawable(new BitmapDrawable());
//        this.mProgressBarGrayHeight = DensityUtil.dip2px(paramContext, 1.0F);
//        this.mProgressBarHeight = DensityUtil.dip2px(paramContext, 2.0F);
//        setValue(500, 500);
//    }
//
//    private void setProgressValue(EditTextLine paramEditTextLine, Message paramMessage) {
//        if (this.focused)
//            paramEditTextLine.setValue(500 - paramMessage.what, 500 + paramMessage.what);
//        while (true) {
//            return;
//            if (paramMessage.what > 1000 - paramMessage.what)
//                continue;
//            paramEditTextLine.setValue(paramMessage.what, 1000 - paramMessage.what);
//        }
//    }
//
//    public void addProgress(EditTextLine paramEditTextLine) {
//        try {
//            this.isFinished = true;
//            SystemClock.sleep(20L);
//            this.isFinished = false;
//            new Thread(new ProgressThread(paramEditTextLine)).start();
//            return;
//        } finally {
//        }
//    }
//
//    protected void drawProgressBar(Canvas paramCanvas) {
//        this.paint.setAntiAlias(true);
//        this.paint.setColor(getResources().getColor(2131493144));
//        paramCanvas.drawRect(new Rect(this.mLeftLimit, 0, this.mRightLimit, this.mProgressBarGrayHeight), this.paint);
//        this.paint.setColor(getResources().getColor(2131493143));
//        paramCanvas.drawRect(new Rect(this.mLeftValue, 0, this.mRightValue, this.mProgressBarHeight), this.paint);
//    }
//
//    protected void onDraw(Canvas paramCanvas) {
//        try {
//            super.onDraw(paramCanvas);
//            drawProgressBar(paramCanvas);
//            return;
//        } finally {
//        }
//    }
//
//    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
//    }
//
//    protected void onMeasure(int paramInt1, int paramInt2) {
//        try {
//            super.onMeasure(paramInt1, paramInt2);
//            int i = View.MeasureSpec.getSize(paramInt1);
//            this.mLeftLimit = this.proPaddingLeftAndRight;
//            this.mRightLimit = (i - this.proPaddingLeftAndRight);
//            setMeasuredDimension(i, this.mProgressBarHeight);
//            return;
//        } finally {
//        }
//    }
//
//    public void setSelected(boolean paramBoolean) {
//        if (paramBoolean) ;
//        for (this.focused = true; ; this.focused = false) {
//            addProgress(this);
//            return;
//        }
//    }
//
//    public void setValue(int paramInt1, int paramInt2) {
//        this.mLeftValue = paramInt1;
//        this.mRightValue = paramInt2;
//        invalidate();
//    }
//
//    class ProgressThread
//            implements Runnable {
//        private EditTextLine editTextLine;
//
//        public ProgressThread(EditTextLine arg2) {
//            Object localObject;
//            this.editTextLine = localObject;
//        }
//
//        public void run() {
//            while ((((Integer) EditTextLine.local.get()).intValue() < 1000) && (!EditTextLine.this.isFinished))
//                synchronized (this.editTextLine) {
//                    Message localMessage = new Message();
//                    EditTextLine.local.set(Integer.valueOf(20 + ((Integer) EditTextLine.local.get()).intValue()));
//                    localMessage.what = ((Integer) EditTextLine.local.get()).intValue();
//                    Log.e("Hong", Thread.currentThread().getName() + " i = " + EditTextLine.local.get());
//                    localMessage.obj = this.editTextLine;
//                    EditTextLine.this.handler.sendMessage(localMessage);
//                    SystemClock.sleep(10L);
//                }
//        }
//    }
//}