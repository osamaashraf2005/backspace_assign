package com.backbase.android.weatherapp.CitiesListActivity;

/**
 * Created by Sam on 27/04/2019.
 * DTO representing cityInfo object
 */

public class CityInfo
{
    private long id;
    private String countryName;
    private String cityName;
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

    private class Coordinate
    {
        private long lat;
        private long lon;

        public long getLat()
        {
            return lat;
        }

        public void setLat(long lat)
        {
            this.lat = lat;
        }

        public long getLon()
        {
            return lon;
        }

        public void setLon(long lon)
        {
            this.lon = lon;
        }
    }
}
