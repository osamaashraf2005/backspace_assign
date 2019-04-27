package com.backbase.android.weatherapp.util;

import android.util.Log;
import com.backbase.android.weatherapp.BuildConfig;

/**
 * Created by Sam on 27/04/2019.
 * Log Util class to control logs for different build flavours
 */

public class LogUtil
{
    private static final String LOG_PREFIX = "backbase_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static boolean LOGGING_ENABLED = !BuildConfig.BUILD_TYPE.equalsIgnoreCase("release");

    // Since logging is disabled for release, we can set our logging level to DEBUG.
    public static int LOG_LEVEL = android.util.Log.DEBUG;

    public static String makeLogTag(String str)
    {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH)
        {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls)
    {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            if (LOG_LEVEL <= Log.DEBUG)
            {
                Log.d(tag, message);
            }
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            if (LOG_LEVEL <= Log.DEBUG)
            {
                Log.d(tag, message, cause);
            }
        }
    }

    public static void LOGV(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            if (LOG_LEVEL <= Log.VERBOSE)
            {
                Log.v(tag, message);
            }
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            if (LOG_LEVEL <= Log.VERBOSE)
            {
                Log.v(tag, message, cause);
            }
        }
    }

    public static void LOGI(final String tag, String message)
    {
        if (LOGGING_ENABLED)
        {
            Log.i(tag, message);
        }
    }

    public static void LOGI(final String tag, String message, Throwable cause)
    {
        if (LOGGING_ENABLED)
        {
            Log.i(tag, message, cause);
        }
    }

    public static void LOGW(final String tag, String message)
    {
        Log.w(tag, message);
    }

    public static void LOGW(final String tag, String message, Throwable cause)
    {
        Log.w(tag, message, cause);
    }

    public static void LOGE(final String tag, String message)
    {
        Log.e(tag, message);
    }

    public static void LOGE(final String tag, String message, Throwable cause)
    {
        Log.e(tag, message, cause);
    }

    private LogUtil()
    {
    }
}
