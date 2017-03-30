package tech.lizhe.lockscreen.activitymanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import tech.lizhe.lockscreen.R;

import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;

public class LockerActivity extends AppCompatActivity {

    private BroadcastReceiver mCloseSystemDialogsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set translucent status bar & navigation bar on post-kitkat devices
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(FLAG_TRANSLUCENT_STATUS);
            window.addFlags(FLAG_TRANSLUCENT_NAVIGATION);
        }
        window.addFlags(FLAG_FULLSCREEN | FLAG_SHOW_WHEN_LOCKED | FLAG_DISMISS_KEYGUARD);
        window.setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_locker);

        // listen for home key & recent key pressed.
        mCloseSystemDialogsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    String reason = intent.getStringExtra("reason");
                    if ("homekey".equals(reason) || "recentapps".equals(reason)) {
                        finish();
                    }
                }
            }
        };
        registerReceiver(mCloseSystemDialogsReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                finish();
            }
        }, 1000 * 10);
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mCloseSystemDialogsReceiver);
    }

    @Override public void onBackPressed() {
        // intercept back key
    }
}
