package com.backbase.android.weatherapp.AboutCompanyActivity;

import com.backbase.android.weatherapp.CitiesListActivity.CityInfo;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * MVP contract for AboutActivity
 */

public interface About {

    interface Model {
        void getAboutInfo();
        void getAboutInfo(CityInfo cityInfo);
    }

    interface Presenter {
        void getAboutInfo();
        void getAboutInfo(CityInfo cityInfo);
        void onSuccess(AboutInfo aboutInfo);
        void onSuccess(CityInfo cityInfo);
        void onFail();
    }

    interface View {
        void setCompanyName(String companyName);
        void setCompanyAddress(String companyAddress);
        void setCompanyPostalCode(String postalCode);
        void setCompanyCity(String companyCity);
        void setAboutInfo(String info);

        /* Adding view functions for showing city information as instructed*/
        void hideCompanyInfo();
        void showCompanyInfo();
        void setCityName(String cityName);
        void setCountryName(String countryName);
        void setCoordinates(String coordinates);
        /* ---------------------------------------------------------------- */

        void showError();
        void showProgress();
        void hideProgress();
    }
}
