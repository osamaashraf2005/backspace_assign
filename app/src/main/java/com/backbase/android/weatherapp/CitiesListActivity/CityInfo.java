package com.backbase.android.weatherapp.CitiesListActivity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sam on 27/04/2019.
 * DTO representing cityInfo object
 */

public class CityInfo implements Serializable
{
    @SerializedName("_id")
    private long id;
    @SerializedName("country")
    private String countryName;
    @SerializedName("name")
    private String cityName;
    @SerializedName("coord")
    private Coordinate coordinate;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate)
    {
        this.coordinate = coordinate;
    }

    public class Coordinate implements Serializable
    {
        @SerializedName("lat")
        private double lat;
        @SerializedName("lon")
        private double lon;

        public double getLat()
        {
            return lat;
        }

        public void setLat(double lat)
        {
            this.lat = lat;
        }

        public double getLon()
        {
            return lon;
        }

        public void setLon(double lon)
        {
            this.lon = lon;
        }

        @Override
        public String toString()
        {
            return String.valueOf(lat + " , " + lon);
        }
    }
}
