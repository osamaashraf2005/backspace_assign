package com.backbase.android.weatherapp.ui.MapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backbase.android.weatherapp.AboutCompanyActivity.AboutActivity;
import com.backbase.android.weatherapp.CitiesListActivity.CityInfo;
import com.backbase.android.weatherapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.backbase.android.weatherapp.CitiesListActivity.CitiesActivity.CITY_INFO_KEY;

/**
 * Created by Sam on 27/04/2019.
 * MapViewFragment for showing city map
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback
{
    private MapView mMapView;

    private CityInfo cityInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = rootView.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        cityInfo = (CityInfo) getArguments().getSerializable(CITY_INFO_KEY);

        try
        {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        rootView.findViewById(R.id.btnAbout).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // For dropping a marker at a point on the Map
        if (cityInfo != null)
        {
            LatLng cityLatLng = new LatLng(cityInfo.getCoordinate().getLat(), cityInfo.getCoordinate().getLon());
            googleMap.addMarker(new MarkerOptions().position(cityLatLng).title(cityInfo.getCityName()).snippet(cityInfo.getCountryName()));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(cityLatLng).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else
        {
            Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void aboutClicked(View view)
    {
        Toast.makeText(getContext(), "Some one clicked me", Toast.LENGTH_SHORT).show();
    }
}