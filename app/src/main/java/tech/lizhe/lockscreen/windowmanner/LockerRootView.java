package tech.lizhe.lockscreen.windowmanner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by lz on 3/29/17.
 */

public class LockerRootView extends RelativeLayout {

    public LockerRootView(Context context) {
        this(context, null);
    }

    public LockerRootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockerRootView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        
    }
}
