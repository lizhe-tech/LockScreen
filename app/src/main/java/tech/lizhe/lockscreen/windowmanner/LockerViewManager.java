package tech.lizhe.lockscreen.windowmanner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.WindowManager;

import tech.lizhe.lockscreen.R;
import tech.lizhe.lockscreen.util.WindowParamUtils;

/**
 * Created by lz on 3/29/17.
 */

public class LockerViewManager {

    private Context mContext;
    private boolean mIsShown = false;
    private LockerRootView mLockerRootView;

    public static LockerViewManager getInstance() {
        return LockerViewHolder.sInstance;
    }

    private static class LockerViewHolder {
        private static final LockerViewManager sInstance = new LockerViewManager();
    }

    private LockerViewManager() {

    }

    public void init(Context context) {
        mContext = context;
    }

    public void showLockerWindow() {
        if (mContext == null || mIsShown) {
            return;
        }
        mIsShown = true;

        mLockerRootView = (LockerRootView) LayoutInflater.from(mContext).inflate(R.layout.window_locker, null);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(mLockerRootView, WindowParamUtils.getLockerParams(mContext));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                hideLockerWindow();
            }
        }, 1000 * 10);
    }

    public void hideLockerWindow() {
        if (mContext == null || !mIsShown) {
            return;
        }
        mIsShown = false;

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(mLockerRootView);
        mLockerRootView = null;
    }
}
