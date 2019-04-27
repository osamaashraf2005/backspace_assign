package com.backbase.android.weatherapp.ui;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

public class UIUtils
{
    public static void whiteNotificationBar(Window window, View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            window.setStatusBarColor(Color.WHITE);
        }
    }
}
