package com.backbase.android.weatherapp.CitiesListActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backbase.android.weatherapp.AboutCompanyActivity.AboutActivity;
import com.backbase.android.weatherapp.MapFragment.MapViewFragment;
import com.backbase.android.weatherapp.R;
import com.backbase.android.weatherapp.data.ApplicationStateData;
import com.backbase.android.weatherapp.ui.CitiesDividerItemDecoration;
import com.backbase.android.weatherapp.ui.UIUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.backbase.android.weatherapp.util.LogUtil.LOGD;
import static com.backbase.android.weatherapp.util.LogUtil.makeLogTag;

public class CitiesActivity extends AppCompatActivity implements ICitiesContract.View, CitiesAdapter.CitiesAdapterListener
{
    private static final String TAG = makeLogTag(CitiesActivity.class);
    public static final String CITY_INFO_KEY = "city_info_key";

    CitiesPresenterImpl mPresenter;

    private MapViewFragment mapFragment;

    private CitiesAdapter mAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvLoading;
    private TextView hintText;
    private SearchView searchView;

    private boolean mapShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cities_list);

        mPresenter = new CitiesPresenterImpl(this, this);

        initUIComponents();

        if (ApplicationStateData.getInstance().isDataCached())
            loadCitiesScene(ApplicationStateData.getInstance().getCities(), ApplicationStateData.getInstance().getStringCityInfoTreeMap());
        else
            mPresenter.getCitiesInfo();
    }

    @Override
    public void loadEmptyScreen()
    {

    }

    @Override
    public void loadCitiesScene(List<CityInfo> cities, TreeMap<String, CityInfo> stringCityInfoTreeMap)
    {
        LOGD(TAG, "No of cities loaded: " + cities.size());

        mAdapter = new CitiesAdapter(cities, stringCityInfoTreeMap, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCitySelected(CityInfo cityInfo)
    {
        showMapFragment(cityInfo);
    }

    @Override
    public void onCityInfoSelected(CityInfo cityInfo)
    {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        aboutIntent.putExtra(CITY_INFO_KEY, cityInfo);
        startActivity(aboutIntent);
    }

    private void showMapFragment(CityInfo cityInfo)
    {
        UIUtils.hideKeyboard(this);
        showHideHintText(false);
        mapShowing = true;
        mapFragment = new MapViewFragment();
        mapFragment.setArguments(getCityBundle(cityInfo));

        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_view, mapFragment).commit();
    }

    private Bundle getCityBundle(CityInfo cityInfo)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CITY_INFO_KEY, cityInfo);
        return bundle;
    }

    private void removeMapFragment()
    {
        mapShowing = false;
        showHideHintText(true);
        getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
    }

    @Override
    public void showError()
    {
        Toast.makeText(this, R.string.err_loading_cities, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHideHintText(boolean show)
    {
        // Hint text will be null in portrait mode, because there is no need for that
        if (hintText != null)
            hintText.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgress()
    {
        progressBar.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        progressBar.setVisibility(View.GONE);
        tvLoading.setVisibility(View.GONE);
    }

    // Showing the Search Menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            // Adding delay for standard 350ms for cancelling the previous search if next button hit
            private Timer timer = new Timer();
            private final long DELAY = 350; // milliseconds

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if (mapShowing)
                    removeMapFragment();

                // filter recycler view when query submitted
                LOGD(TAG, "Submitting Query: " + query);
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query)
            {
                if (mapShowing)
                    removeMapFragment();

                // filter recycler view when text is changed
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        LOGD(TAG, "Running Query: " + query.toLowerCase());
                                        mAdapter.getFilter().filter(query.toLowerCase());
                                    }
                                });
                            }
                        }, DELAY);
                return false;
            }
        });
        return true;
    }

    // Handling the back press
    @Override
    public void onBackPressed()
    {
        // Removing Map Fragment if visible
        if (mapShowing)
        {
            removeMapFragment();
        } else
        {
            // close search view on back button pressed
            if (!searchView.isIconified())
            {
                searchView.setIconified(true);
            } else
            {
                super.onBackPressed();
            }
        }
    }

    //Initializing UI Components
    private void initUIComponents()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.toolbar_title);
        }

        ViewGroup mainContent = findViewById(R.id.mainContent);
        recyclerView = mainContent.findViewById(R.id.recycler_view);
        progressBar = mainContent.findViewById(R.id.toolbarprogress);
        tvLoading = mainContent.findViewById(R.id.tvLoading);

        hintText = findViewById(R.id.hintText);
        // white background notification bar
        UIUtils.whiteNotificationBar(getWindow(), recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CitiesDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));

        recyclerView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                UIUtils.hideKeyboard(CitiesActivity.this);
                return false;
            }
        });
    }
}
