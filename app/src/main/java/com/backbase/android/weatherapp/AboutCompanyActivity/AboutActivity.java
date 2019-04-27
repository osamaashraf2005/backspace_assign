package com.backbase.android.weatherapp.AboutCompanyActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.backbase.android.weatherapp.CitiesListActivity.CityInfo;
import com.backbase.android.weatherapp.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import static com.backbase.android.weatherapp.CitiesListActivity.CitiesActivity.CITY_INFO_KEY;


public class AboutActivity extends AppCompatActivity implements About.View
{
    private android.view.View infoContainer;
    private TextView companyName;
    private TextView companyAddress;
    private TextView companyPostal;
    private TextView companyCity;
    private TextView aboutInfo;

    private android.view.View cityInformation;
    private TextView tvCityName;
    private TextView tvCountryName;
    private TextView tvCoordinates;

    private ProgressBar progressBar;
    private android.view.View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        AboutPresenterImpl aboutPresenter = new AboutPresenterImpl(this, this);


        infoContainer = findViewById(R.id.infoContainer);

        /* Re-factoring find view by id by checking into infoContainer View Group*/
        companyName = infoContainer.findViewById(R.id.companyName);
        companyAddress = infoContainer.findViewById(R.id.companyAdress);
        companyPostal = infoContainer.findViewById(R.id.companypostal);
        companyCity = infoContainer.findViewById(R.id.companyCity);
        aboutInfo = infoContainer.findViewById(R.id.aboutInfo);
        /*-------------------------------------------------------------------------*/

        cityInformation = findViewById(R.id.cityInformation);
        tvCityName = cityInformation.findViewById(R.id.cityName);
        tvCountryName = cityInformation.findViewById(R.id.countryName);
        tvCoordinates = cityInformation.findViewById(R.id.coordinates);

        progressBar = findViewById(R.id.progressBar);
        errorView = findViewById(R.id.errorView);

        if (getIntent().hasExtra(CITY_INFO_KEY))
        {
            CityInfo cityInfo = (CityInfo) Objects.requireNonNull(getIntent().getExtras()).get(CITY_INFO_KEY);
            aboutPresenter.getAboutInfo(cityInfo);
        } else
            aboutPresenter.getAboutInfo();
    }

    @Override
    public void setCompanyName(String companyNameString)
    {
        infoContainer.setVisibility(android.view.View.VISIBLE);
        companyName.setText(companyNameString);
    }

    @Override
    public void setCompanyAddress(String companyAddressString)
    {
        companyAddress.setText(companyAddressString);
    }

    @Override
    public void setCompanyPostalCode(String postalCodeString)
    {
        companyPostal.setText(postalCodeString);
    }

    @Override
    public void setCompanyCity(String companyCityString)
    {
        companyCity.setText(companyCityString);
    }

    @Override
    public void setAboutInfo(String infoString)
    {
        aboutInfo.setText(infoString);
    }

    @Override
    public void hideCompanyInfo()
    {
        cityInformation.setVisibility(View.VISIBLE);
        infoContainer.setVisibility(View.GONE);
    }

    @Override
    public void showCompanyInfo()
    {
        infoContainer.setVisibility(View.VISIBLE);
        cityInformation.setVisibility(View.GONE);
    }

    @Override
    public void setCityName(String cityName)
    {
        tvCityName.setText(cityName);
    }

    @Override
    public void setCountryName(String countryName)
    {
        tvCountryName.setText(countryName);
    }

    @Override
    public void setCoordinates(String coordinates)
    {
        tvCoordinates.setText(coordinates);
    }

    @Override
    public void showError()
    {
        errorView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showProgress()
    {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        progressBar.setVisibility(android.view.View.GONE);
    }
}
