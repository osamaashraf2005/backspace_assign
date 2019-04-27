package com.backbase.android.weatherapp.CitiesListActivity;

import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;

public class CitiesPresenterImpl implements ICitiesContract.Presenter
{
    private final WeakReference<ICitiesContract.View> citiesView;
    private final CitiesModelImpl citiesModel;

    public CitiesPresenterImpl(ICitiesContract.View view, @NonNull Context context)
    {
        this.citiesView = new WeakReference<>(view);
        this.citiesModel = new CitiesModelImpl(this, view, context);
    }

    @Override
    public void getCitiesInfo()
    {
        ICitiesContract.View citiesViewImpl = citiesView.get();

        citiesViewImpl.showProgress();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                citiesModel.getCitiesInfo();
            }
        }, 1000);
    }

    @Override
    public void onSuccess(List<CityInfo> citiesInfo, TreeMap<String, CityInfo> stringCityInfoTreeMap) //Not using SortedList because we are not adding/removing items frequently
    {
        ICitiesContract.View citiesViewImpl = citiesView.get();

        if (citiesViewImpl != null)
        {
            citiesViewImpl.hideProgress();

            if (citiesInfo != null && citiesInfo.size() > 0)
            {
                citiesViewImpl.loadCitiesScene(citiesInfo, stringCityInfoTreeMap);
            } else
            {
                citiesViewImpl.loadEmptyScreen();
            }
        }
    }

    @Override
    public void onFail()
    {
        ICitiesContract.View citiesViewImpl = citiesView.get();
        if (citiesViewImpl != null)
        {
            citiesViewImpl.hideProgress();
            citiesViewImpl.showError();
        }
    }
}
