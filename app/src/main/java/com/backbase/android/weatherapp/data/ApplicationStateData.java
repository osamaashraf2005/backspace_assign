package com.backbase.android.weatherapp.data;

import android.app.Application;

import static com.backbase.android.weatherapp.util.LogUtil.LOGD;
import static com.backbase.android.weatherapp.util.LogUtil.makeLogTag;

/**
 * Created by Sam on 27/04/2019.
 * Base class to maintaining global application state
 */

public class ApplicationStateData extends Application
{
    private static final String TAG = makeLogTag(ApplicationStateData.class);

    private static ApplicationStateData applicationStateData;

    @Override
    public void onCreate()
    {
        super.onCreate();

        LOGD(TAG, "Application Context Created");

        applicationStateData = this;
    }

    public static ApplicationStateData getInstance()
    {
        return applicationStateData;
    }
}
