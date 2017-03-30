package tech.lizhe.lockscreen.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by lz on 3/28/17.
 */

public class CommonUtils {

    public static int getPhoneHeight(Context context) {
        int height = context.getResources().getDisplayMetrics().heightPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            Point localPoint = new Point();
            windowManager.getDefaultDisplay().getRealSize(localPoint);
            if (localPoint.y > height) {
                height = localPoint.y;
            }
        } else {
            int navigationBarHeight = getNavigationBarHeight(context);
            if (navigationBarHeight != 0 && height % 10 != 0) {
                if ((height + navigationBarHeight) % 10 == 0) {
                    height = (height + navigationBarHeight);
                }
            }
        }
        return height;
    }

    public static int getPhoneWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getNavigationBarHeight(Context context) {
        if (null == context) {
            return 0;
        }
        Resources localResources = context.getResources();
        if (!hasNavBar(context)) {
            return 0;
        }
        int i = localResources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (i > 0) {
            return localResources.getDimensionPixelSize(i);
        }
        i = localResources.getIdentifier("navigation_bar_height_landscape", "dimen", "android");
        if (i > 0) {
            return localResources.getDimensionPixelSize(i);
        }
        return 0;
    }

    public static boolean hasNavBar(Context paramContext) {
        boolean bool = true;
        String sNavBarOverride;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Object localObject = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
                ((Method) localObject).setAccessible(true);
                sNavBarOverride = (String) ((Method) localObject).invoke(null, "qemu.hw.mainkeys");
                localObject = paramContext.getResources();
                int i = ((Resources) localObject).getIdentifier("config_showNavigationBar", "bool", "android");
                if (i != 0) {
                    bool = ((Resources) localObject).getBoolean(i);
                    if ("1".equals(sNavBarOverride)) {
                        return false;
                    }
                }
            } catch (Throwable localThrowable) {
            }
        }
        if (!ViewConfiguration.get(paramContext).hasPermanentMenuKey()) {
            return bool;
        }
        return false;
    }
}
