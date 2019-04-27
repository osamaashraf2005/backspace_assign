package com.backbase.android.weatherapp.CitiesListActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backbase.android.weatherapp.AboutCompanyActivity.AboutActivity;
import com.backbase.android.weatherapp.MapFragment.MapViewFragment;
import com.backbase.android.weatherapp.R;
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
    private SearchView searchView;


    private boolean mapShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cities_list);

        mPresenter = new CitiesPresenterImpl(this, this);

        initUIComponents();

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
        startActivity(aboutIntent);
    }

    private void showMapFragment(CityInfo cityInfo)
    {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        mapShowing = true;
        mapFragment = new MapViewFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(CITY_INFO_KEY, cityInfo);
        mapFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_view, mapFragment).commit();
    }

    private void removeMapFragment()
    {
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
        mapShowing = false;
        getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
    }

    @Override
    public void showError()
    {
        Toast.makeText(this, R.string.err_loading_cities, Toast.LENGTH_SHORT).show();
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
            private Timer timer = new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // filter recycler view when query submitted
                LOGD(TAG, "Submitting Query: " + query);
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query)
            {
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
        if (mapShowing)
        {
            removeMapFragment();
        } else
        {
            super.onBackPressed();
            // close search view on back button pressed
            if (!searchView.isIconified())
            {
                searchView.setIconified(true);
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
        // white background notification bar
        UIUtils.whiteNotificationBar(getWindow(), recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CitiesDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
    }
}
