package com.backbase.android.weatherapp.util;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Sam on 28/04/2019.
 * TestUtil class to notify test running or real app
 */

public class TestUtil
{
    private AtomicBoolean isRunningTest;

    public synchronized boolean isRunningTest()
    {
        if (null == isRunningTest)
        {
            boolean istest;

            try
            {
                Class.forName("com.backbase.android.weatherapp.SearchCitiesTest");
                istest = true;
            } catch (ClassNotFoundException e)
            {
                istest = false;
            }

            isRunningTest = new AtomicBoolean(istest);
        }

        return isRunningTest.get();
    }
}
