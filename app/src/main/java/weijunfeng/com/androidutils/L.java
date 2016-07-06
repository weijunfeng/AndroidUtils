package weijunfeng.com.androidutils;

import android.util.Log;

import java.util.Locale;

public class L {
    private static final String TAG = L.class.getSimpleName();

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug) Log.i(generateTag() + TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug) Log.d(generateTag() + TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug) Log.e(generateTag() + TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug) Log.v(generateTag() + TAG, msg);
    }

    public static final void i(String tag, String msg) {
        if (null != msg) {
            Log.i(generateTag() + tag, msg);
        }
    }

    private static String generateTag() {
//        StackTraceElement caller = new Throwable().getStackTrace()[2];
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "(%s)%s.%s(L:%d): ";
        tag = String.format(Locale.getDefault(), tag, Thread.currentThread().getId(), callerClazzName, caller.getMethodName(), caller
                .getLineNumber());
        return tag;
    }
}
