package tech.lizhe.lockscreen.windowmanner;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service for maintain locker alive.
 */
public class LockServicePhantom extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification localNotification = LockNotificationMgr.getInstance().getForegroundNotification();
        startForeground(LockNotificationMgr.NOTIFICATION_ID_FOREGROUND, localNotification);
        try {
            stopForeground(true);
            stopSelf();
        } catch (Exception exception) {
            
        }
        return START_NOT_STICKY;
    }
}
