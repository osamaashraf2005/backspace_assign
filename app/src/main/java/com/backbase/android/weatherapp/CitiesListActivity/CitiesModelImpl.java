package com.backbase.android.weatherapp.CitiesListActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;

public class CitiesModelImpl implements ICitiesContract.Model
{
    private static final String TAG = CitiesModelImpl.class.getSimpleName();
    private final ICitiesContract.Presenter presenter;
    private final ICitiesContract.View view;


    private final WeakReference<Context> context;
    private static final String FILE_NAME = "cities.json";

    CitiesModelImpl.InitialTask mInitialTask;

    public CitiesModelImpl(@NonNull ICitiesContract.Presenter presenter, @NonNull ICitiesContract.View view, @NonNull Context context)
    {
        this.presenter = presenter;
        this.view = view;
        this.context = new WeakReference<>(context);
    }

    @Override
    public void getCitiesInfo()
    {
        if (mInitialTask == null || mInitialTask.getStatus() != AsyncTask.Status.RUNNING)
        {
            mInitialTask = new CitiesModelImpl.InitialTask(context.get(), presenter, view);
            mInitialTask.execute();
        }
    }

    @Override
    public void stop()
    {
        /* Call this method on onDestroy() of your Activity/Fragment.
           Stop any running Async task here */
        if (mInitialTask != null && mInitialTask.getStatus() == AsyncTask.Status.RUNNING)
        {
            mInitialTask.cancel(true);
        }
    }

    public static class InitialTask extends AsyncTask<Void, Integer, List<CityInfo>>
    {
        public static final int STATE_LOADING = 1;
        public static final int STATE_EMPTY = 2;
        public static final int STATE_SHOW_CITIES = 3;

        private final Gson gson = new Gson();

        private final ICitiesContract.Presenter presenter;
        private final ICitiesContract.View view;

        private TreeMap<String, CityInfo> stringCityInfoTreeMap = new TreeMap<>();
        private final WeakReference<Context> context;

        public InitialTask(Context context, ICitiesContract.Presenter presenter, ICitiesContract.View view)
        {
            this.context = new WeakReference<>(context);
            this.presenter = presenter;
            this.view = view;
        }


        @Override
        protected List<CityInfo> doInBackground(Void... voids)
        {
            publishProgress(STATE_LOADING);

            String citiesInfoJson = getAboutInfoFromAssets();

            final List<CityInfo> cities = (citiesInfoJson != null && !citiesInfoJson.isEmpty()) ? parseCitiesInfo(citiesInfoJson) : null;

            if (cities != null)
            {
                //Sorting Cities
                //Since there was a condition on the test isntruction "Loading time of the app is not so important."
                // so sorting it through collections.sort
//                Collections.sort(cities, new Comparator<CityInfo>()
//                {
//                    @Override
//                    public int compare(CityInfo city1, CityInfo city2)
//                    {
//                        return city1.getCityName().toLowerCase().compareTo(city2.getCityName().toLowerCase());
//                    }
//                });

                /* Making the city name and city info tree map at the app loading because
                   "Loading time of the app is not so important." */

                for (CityInfo cityInfo : cities)
                    stringCityInfoTreeMap.put(cityInfo.getCityName().toLowerCase(), cityInfo);
            }

            return cities;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            switch (values[0])
            {
                case STATE_LOADING:
                    view.showProgress();
                    break;
                case STATE_EMPTY:
                    view.loadEmptyScreen();
                    break;
            }
        }

        @Override
        protected void onPostExecute(List<CityInfo> cityInfos)
        {
            super.onPostExecute(cityInfos);

            if (cityInfos != null && cityInfos.size() > 0)
            {
                presenter.onSuccess(cityInfos, stringCityInfoTreeMap);
            } else
            {
                presenter.onFail();
            }
        }

        private List<CityInfo> parseCitiesInfo(String citiesJsonString)
        {
            Type type = new TypeToken<ArrayList<CityInfo>>()
            {
            }.getType();

            return gson.fromJson(citiesJsonString, type);
        }

        public String getAboutInfoFromAssets()
        {

            if (context.get() != null)
            {
                try
                {
                    AssetManager manager = context.get().getAssets();
                    InputStream file = manager.open(FILE_NAME);
                    byte[] formArray = new byte[file.available()];
                    file.read(formArray);
                    file.close();
                    return new String(formArray);
                } catch (IOException ex)
                {
                    Log.e(TAG, ex.getLocalizedMessage(), ex);
                }
            }

            return null;
        }

    }
}
