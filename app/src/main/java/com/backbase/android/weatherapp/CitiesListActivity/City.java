package com.backbase.android.weatherapp.CitiesListActivity;

import java.util.List;

/**
 * Created by Sam on 27/04/2019.
 * MVP contract for CityActivity
 */

public interface City
{
    interface Model
    {
        void getCitiesInfo();
    }

    interface Presenter
    {
        void getCitiesInfo();
        void onSuccess(List<CityInfo> citiesInfo);
        void onFail();
    }

    interface View
    {
        void setCompanyName(String companyName);
        void setCompanyAddress(String companyAddress);
        void setCompanyPostalCode(String postalCode);
        void setCompanyCity(String companyCity);
        void setAboutInfo(String info);
        void showError();
        void showProgress();
        void hideProgress();
    }
}
