package com.backbase.android.weatherapp.data;

import android.app.Application;

import com.backbase.android.weatherapp.CitiesListActivity.CityInfo;

import java.util.List;
import java.util.TreeMap;

import static com.backbase.android.weatherapp.util.LogUtil.LOGD;
import static com.backbase.android.weatherapp.util.LogUtil.makeLogTag;

/**
 * Created by Sam on 27/04/2019.
 * Singleton class to maintaining global application state and caching data
 */

public class ApplicationStateData extends Application
{
    private static final String TAG = makeLogTag(ApplicationStateData.class);

    private static ApplicationStateData applicationStateData;

    /* Caching data for not loading them again */
    private List<CityInfo> cities;
    private TreeMap<String, CityInfo> stringCityInfoTreeMap;
    private boolean isDataCached;

    @Override
    public void onCreate()
    {
        super.onCreate();

        LOGD(TAG, "Application Context Created");

        applicationStateData = this;
    }

    public List<CityInfo> getCities()
    {
        return cities;
    }

    public void setCities(List<CityInfo> cities)
    {
        this.cities = cities;
    }

    public TreeMap<String, CityInfo> getStringCityInfoTreeMap()
    {
        return stringCityInfoTreeMap;
    }

    public void setStringCityInfoTreeMap(TreeMap<String, CityInfo> stringCityInfoTreeMap)
    {
        this.stringCityInfoTreeMap = stringCityInfoTreeMap;
    }

    public void setDataCached(boolean dataCached)
    {
        isDataCached = dataCached;
    }

    public boolean isDataCached()
    {
        return isDataCached;
    }

    public static synchronized ApplicationStateData getInstance()
    {
        return applicationStateData;
    }

}
