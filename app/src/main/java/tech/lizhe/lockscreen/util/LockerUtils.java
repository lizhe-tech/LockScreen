package tech.lizhe.lockscreen.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import tech.lizhe.lockscreen.LockerApplication;
import tech.lizhe.lockscreen.activitymanner.LockerActivity;
import tech.lizhe.lockscreen.windowmanner.LockerViewManager;

/**
 * Created by lz on 3/28/17.
 */

public class LockerUtils {

    public static void startLockerActivity() {
        Intent intent = new Intent(LockerApplication.getContext(), LockerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        LockerApplication.getContext().startActivity(intent);
    }

    public static void onScreenOn() {
        startLockerActivity();
        LockerViewManager.getInstance().showLockerWindow();
    }

    public static void onScreenOff() {
        startLockerActivity();
        LockerViewManager.getInstance().showLockerWindow();
    }

    @SuppressLint("NewApi")
    public static boolean isFloatWindowAllowed(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context)
                || getHuaweiEmuiVersionCode() == EmuiBuild.VERSION_CODES.EMUI_4_1;
    }

    public static int getHuaweiEmuiVersionCode() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            String versionStr = prop.getProperty(EmuiBuild.BUILD_PROP_NAME, null);
            return Integer.valueOf(versionStr);
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    private static class BuildProperties {
        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }

    /**
     * Huawei EMUI devices.
     */
    public static class EmuiBuild {
        private static final String BUILD_PROP_NAME = "ro.build.hw_emui_api_level";

        public static class VERSION_CODES {
            /**
             * EMUI 4.0 (API 23).
             */
            public static final int EMUI_4_0 = 9;

            /**
             * EMUI 4.1 (API 23).
             */
            public static final int EMUI_4_1 = 10;

            /**
             * EMUI 5.0 (API 24).
             */
            public static final int EMUI_5_0 = 11;
        }
    }
}
