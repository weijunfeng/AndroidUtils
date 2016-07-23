package weijunfeng.com.androidutils.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import weijunfeng.com.androidutils.R;

import static android.R.attr.gravity;

/**
 * Created by huotu on 2016/5/4.
 */
public class PopupWindow extends Activity {
    /*
   显示红包
    */
    private void showHB() {
        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource(R.drawable.hb_bg);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dip2px(this, 30), DensityUtil.dip2px(this, 30)));
        final android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(imageView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT, true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.money_receive);
        mediaPlayer.start();
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        popupWindow.showAtLocation(imageView, Gravity.CENTER, 0, 0);
        changeWindowAlpha(1.0f, 0.5f);
        popupWindow.setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlpha(0.5f, 1.0f);
                mediaPlayer.release();
            }
        });
    }

    public void showPop() {
        View contentView = View.inflate(this, R.layout.activity_main, null);
        android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 点击popupWindow范围以外的地方,让popupWindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置动画
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        //设置view
        popupWindow.setContentView(contentView);
        //设置消失监听
        popupWindow.setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //PopupWindow显示的方法有三个, showAsDropDown(anchor), showAsDropDown(anchor, xoff, yoff)和showAtLocation(parent, gravity, x, y)。
//        前两个showAsDropDown方法是让PopupWindow相对于某个控件显示，而showAtLocation是相对于整个窗口的。
//        第一个参数是View类型的parent, 虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，并不要求这个parent是一个ViewGroup，这个参数名让人误解。官方文档”
//        a parent view to get the android.view.View.getWindowToken() token from
// “,这个parent的作用应该是调用其getWindowToken() 方法获取窗口的Token, 所以，只要是该窗口上的控件就可以了。
//        第二个参数是Gravity，可以使用 | 附加多个属性，如Gravity.LEFT | Gravity.BOTTOM。
//        第三四个参数是x, y偏移
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //消失
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }


    }

    private void changeWindowAlpha(float startAlpha, float endAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            final WindowManager.LayoutParams attributes = getWindow().getAttributes();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha);
            valueAnimator.setDuration(200);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        attributes.alpha = (float) (Float) animation.getAnimatedValue();
                        getWindow().setAttributes(attributes);
                    }
                }
            });
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.start();
        }
    }
}
