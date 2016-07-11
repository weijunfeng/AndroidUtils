package weijunfeng.com.androidutils.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 带清空的edittext
 * 1、方法一(如果输入法在窗口上已经显示，则隐藏，反之则显示)
 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);



 2、方法二（view为接受软键盘输入的视图，SHOW_FORCED表示强制显示）
 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);



imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘



 3、调用隐藏系统默认的输入法
((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(WidgetSearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  (WidgetSearchActivity是当前的Activity)


 4、获取输入法打开的状态
InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开

 解决之道：在EditText的父级控件中找一个，设置成

 android:focusable="true"
 android:focusableInTouchMode="true"

 这样，就把EditText默认的行为截断了！

 */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private OnFocusChangeListener mFocusListener;
    private TextWatcher mWatcher;
    private Context mContext;

    public ClearEditText(Context context) {
        this(context, null, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//            mClearDrawable = getResources().getDrawable(R.drawable.clear_btn);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mFocusListener = l;
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        mWatcher = watcher;
    }

    private void setClearIconVisible(boolean b) {
        Drawable right = b ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    public void setClearDrawableVisible(boolean visible) {
        if (!visible) {
            mClearDrawable = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()
                        && event.getX() < getWidth() - getPaddingRight();
                if (touchable) {
                    setText("");
                    clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mFocusListener != null) {
            mFocusListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mWatcher != null) {
            mWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean visible = getText().length() > 0;
        setClearIconVisible(visible);
        if (mWatcher != null) {
            mWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mWatcher != null) {
            mWatcher.afterTextChanged(s);
        }
    }
}
