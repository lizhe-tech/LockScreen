package tech.lizhe.lockscreen.windowmanner;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import tech.lizhe.lockscreen.MainActivity;
import tech.lizhe.lockscreen.R;

@SuppressLint("NewApi")
public class LockNotificationMgr {

    public static final int NOTIFICATION_ID_FOREGROUND = 1;

    private static LockNotificationMgr instance;

    private Context context;

    private Notification foregroundNotification;

    public static synchronized LockNotificationMgr init(Context context) {
        if (instance == null) {
            instance = new LockNotificationMgr(context);
        }

        return instance;
    }

    public static LockNotificationMgr getInstance() {
        return instance;
    }

    private LockNotificationMgr(Context context) {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public Notification getForegroundNotification() {
        if (null == foregroundNotification) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                foregroundNotification = new Notification(0, null, System.currentTimeMillis());
                foregroundNotification.flags |= Notification.FLAG_NO_CLEAR;
            } else {
                Intent resultIntent = new Intent(context, MainActivity.class);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                foregroundNotification = createNotification(context.getString(R.string.app_name),
                        context.getString(R.string.app_name), null, -1, null, false,
                        NotificationCompat.PRIORITY_MIN, R.mipmap.ic_launcher, -1, resultPendingIntent);
            }
        }
        return foregroundNotification;
    }

    @SuppressWarnings("deprecation")
    private Notification createNotification(String title, String content, String ticker, long date, Uri soundUri, boolean useDefaultSound, int priority, int smallIconRes, int largeIconRes, PendingIntent pendingIntent) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(smallIconRes);
        if (largeIconRes > 0) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIconRes));//R.drawable.notification_large_icon));
        }
        if (!TextUtils.isEmpty(ticker)) {
            builder.setTicker(ticker);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        if (date > 0) {
            builder.setWhen(date);
        }
        if (null != soundUri) {
            builder.setSound(soundUri);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(priority);
        }

        Notification notification = builder.getNotification();
        if (null == soundUri && useDefaultSound) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }

        return notification;
    }
}
