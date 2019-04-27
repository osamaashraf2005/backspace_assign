package com.backbase.android.weatherapp.CitiesListActivity;

import java.util.List;

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
        void onSuccess(List<CityInfo> citiesInfo);
        void onFail();
    }

    interface View
    {
        void loadEmptyScreen();
        void loadCitiesScene(List<CityInfo> cityInfos);
        void showError();
        void showProgress();
        void hideProgress();
    }
}
