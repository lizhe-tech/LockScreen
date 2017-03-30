package tech.lizhe.lockscreen.util;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

public class WindowParamUtils {

    /**
     * Construct locker float window's WindowManager.LayoutParams.
     * @param context
     * @return
     */
    public static WindowManager.LayoutParams getLockerParams(Context context) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        int phoneWidth = CommonUtils.getPhoneWidth(context);
        int phoneHeight = CommonUtils.getPhoneHeight(context);
        layoutParams.width = phoneWidth < phoneHeight ? phoneWidth : phoneHeight;
        layoutParams.height = phoneWidth > phoneHeight ? phoneWidth : phoneHeight;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        if (Build.VERSION.SDK_INT <= 18) {
            layoutParams.flags ^= WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        return layoutParams;
    }

    /**
     * Construct an empty view's WindowManager.LayoutParams.
     * @param context
     * @return
     */
    public static WindowManager.LayoutParams getEmptyParams(Context context) {
        WindowManager.LayoutParams emptyParams = new WindowManager.LayoutParams();
        emptyParams.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
        emptyParams.height = 1;
        emptyParams.width = 1;
        emptyParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        emptyParams.format = PixelFormat.TRANSPARENT;
        return emptyParams;
    }
}
