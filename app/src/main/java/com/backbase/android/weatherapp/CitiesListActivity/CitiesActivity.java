package com.backbase.android.weatherapp.CitiesListActivity;

import android.os.Bundle;

import com.backbase.android.weatherapp.R;
import com.backbase.android.weatherapp.ui.CitiesDividerItemDecoration;
import com.backbase.android.weatherapp.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesActivity extends AppCompatActivity implements ICitiesContract.View
{
    private static final String FILE_PATH = "";

    private CitiesPresenterImpl mPresenter;

    private RecyclerView recyclerView;

    private ArrayList<CityInfo> citiesList;

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
    public void loadCitiesScene(List<CityInfo> cities)
    {
//        citiesList = new ArrayList<>();
//        mAdapter = new ContactsAdapter(this, contactList, this);
//        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError()
    {

    }

    @Override
    public void showProgress()
    {

    }

    @Override
    public void hideProgress()
    {

    }

    private void initUIComponents()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerView = findViewById(R.id.recycler_view);

        // white background notification bar
        UIUtils.whiteNotificationBar(getWindow(), recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CitiesDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
    }
}
