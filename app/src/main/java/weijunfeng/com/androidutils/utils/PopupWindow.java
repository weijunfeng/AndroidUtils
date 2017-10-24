package weijunfeng.com.androidutils.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.*;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import weijunfeng.com.androidutils.R;

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
//        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.money_receive);
//        mediaPlayer.start();
//        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
//        popupWindow.showAtLocation(imageView, Gravity.CENTER, 0, 0);
//        changeWindowAlpha(1.0f, 0.5f);
//        popupWindow.setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                changeWindowAlpha(0.5f, 1.0f);
//                mediaPlayer.release();
//            }
//        });
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

    /**
     * 本质区别为：Dialog是非阻塞式对话框：Dialog弹出时，后台还可以做事情；
     * 而PopupWindow是阻塞式对话框：PopupWindow弹出时，程序会等待，在PopupWindow退出前，程序一直等待，
     * 只有当我们调用了dismiss方法的后，PopupWindow退出，程序才会向下执行。
     * 这两种区别的表现是：Dialog弹出时，背景是黑色的，但是当我们点击背景，Dialog会消失，
     * 证明程序不仅响应Dialog的操作，还响应其他操作，其他程序没有被阻塞，这说明了Dialog是非阻塞式对话框；
     * PopupWindow弹出时，背景没有什么变化，
     * 但是当我们点击背景的时候，程序没有响应，只允许我们操作PopupWindow，其他操作被阻塞。
     * PopupWindow默认没有获得焦点能力，如果这时PopupWindow的内容中有EidtText，需要输入，
     * 这是是无法输入的；只有为true的时候，PopupWindow才具有获得焦点能力，EditText才是真正的EditText
     * Dialog没法设置宽为整个屏幕宽，总有点边界。Popupwindow可以(PopupWindow也可以设置有边界)。
     * <p>
     * PopupWindow可以用来显示任意视图。出现在当前Activity上的一个浮动容器
     * 适用场景：输入的补全信息、下拉选择的菜单，可以是一些提示的信息。
     * Dialog也是一个小号窗口，用于提示用户做一些额外的输出信息或者监察决定。通常情况下不填满屏幕，要求用户做出行动才能继续进行。
     * 适用场景：输入账号密码、请求权限、警告等，总之是需要用户明确知道一些信息，用户做进一步操作前，需要确定或者填入信息。
     * <p>
     * Dialog已经不推荐使用了，因为使用它在横竖屏切换等场合容易发生窗体泄漏;
     * Dialog的替代是DialogFragment，它不会出现窗体泄漏的问题，而且兼具Dialog和Fragment的特点，
     * 即你既可以通过重写onCreateDialog来创建，也可以通过重写onCreateView来创建；但是注意不能同时重写这两个方法
     * 2.PopupWindiw常用于某些需要特殊形状弹出窗的场合，像平常看到的很多半透明窗体都是利用PopupWindow制作的。
     * <p>
     * （1）Popupwindow在显示之前一定要设置宽高，Dialog无此限制。
     * <p>
     * （2）Popupwindow默认不会响应物理键盘的back，除非显示设置了popup.setFocusable(true);而在点击back的时候，Dialog会消失。
     * <p>
     * （3）Popupwindow不会给页面其他的部分添加蒙层，而Dialog会。
     * <p>
     * （4）Popupwindow没有标题，Dialog默认有标题，可以通过dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);取消标题
     * <p>
     * （5）二者显示的时候都要设置Gravity。如果不设置，Dialog默认是Gravity.CENTER。
     * （6）二者都有默认的背景，都可以通过setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));去掉。
     */
    private void showDialog() {
        Dialog dialog = new Dialog(this, R.style.confirmDialog);
//        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
//        dialog.setContentView(inflate);
//        //寻找view
//        dialog.findViewById(R.id.sss);
        // 点击popupWindow范围以外的地方,让popupWindow消失
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //通过window设置动画,显示位置
        Window window = dialog.getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();

    }
}
