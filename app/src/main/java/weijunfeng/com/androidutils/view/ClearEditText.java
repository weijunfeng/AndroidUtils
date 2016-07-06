package weijunfeng.com.androidutils.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * 带清空的edittext
 */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private OnFocusChangeListener mFocusListener;
    private TextWatcher mWatcher;

    public ClearEditText(Context context) {
        this(context, null, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
