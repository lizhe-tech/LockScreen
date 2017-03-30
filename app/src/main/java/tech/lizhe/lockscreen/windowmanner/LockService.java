package tech.lizhe.lockscreen.windowmanner;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import tech.lizhe.lockscreen.LockerApplication;
import tech.lizhe.lockscreen.util.LockerUtils;

@SuppressWarnings("deprecation")
public class LockService extends Service {

    private static final String TAG = LockService.class.getSimpleName();

    private static final String KEYGUARD_LOCK_NAME = "KeyguardLock";
    private static final String START_ACTION = "tech.lizhe.lockscreen.LockService";

    private boolean setForeground;
    private KeyguardLock keyguardLock;

    @Override
    public void onCreate() {
        super.onCreate();

        LockNotificationMgr.init(this);
        LockerViewManager.getInstance().init(this);

        KeyguardManager keyguardMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        keyguardLock = keyguardMgr.newKeyguardLock(KEYGUARD_LOCK_NAME);
        try {
            keyguardLock.disableKeyguard();
        } catch (Exception e) {
            keyguardLock = null;
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (LockerUtils.isFloatWindowAllowed(LockerApplication.getContext())) {
            startService(getIntent());
        } else {
            if (keyguardLock != null) {
                keyguardLock.reenableKeyguard();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!setForeground) {
            startForeground(LockNotificationMgr.NOTIFICATION_ID_FOREGROUND, LockNotificationMgr.getInstance().getForegroundNotification());
            startService(new Intent(this, LockServicePhantom.class));
            setForeground = true;
        }
        return START_STICKY;
    }

    public static Intent getIntent() {
        Intent intent = new Intent();
        intent.setAction(START_ACTION);
        intent.setPackage(LockerApplication.getContext().getPackageName());
        return intent;
    }
}
