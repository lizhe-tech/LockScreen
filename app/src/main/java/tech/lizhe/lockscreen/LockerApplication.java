package tech.lizhe.lockscreen;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import tech.lizhe.lockscreen.util.LockerUtils;
import tech.lizhe.lockscreen.windowmanner.LockService;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;

/**
 * Created by lz on 3/28/17.
 */

public class LockerApplication extends Application {

    private static Context sContext;

    @Override public void onCreate() {
        super.onCreate();

        final IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenFilter.setPriority(SYSTEM_HIGH_PRIORITY);

        // no need to unregister as application doesn't have an onDestroy callback
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    LockerUtils.onScreenOff();
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    LockerUtils.onScreenOn();
                }
            }
        }, screenFilter);

        startService(LockService.getIntent());
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }
}
