package com.backbase.android.weatherapp.CitiesListActivity;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by Sam on 27/04/2019.
 * MVP contract for CityActivity
 */

public interface ICitiesContract
{
    interface Model
    {
        void getCitiesInfo();
        void stop();
    }

    interface Presenter
    {
        void getCitiesInfo();
        void onSuccess(List<CityInfo> citiesInfo, TreeMap<String, CityInfo> stringCityInfoTreeMap);
        void onFail();
    }

    interface View
    {
        void loadEmptyScreen();
        void loadCitiesScene(List<CityInfo> cityInfos, TreeMap<String, CityInfo> stringCityInfoTreeMap);//Not using SortedList because we are not adding/removing items frequently
        void showError();
        void showHideHintText(boolean show);
        void showProgress();
        void hideProgress();
    }
}
