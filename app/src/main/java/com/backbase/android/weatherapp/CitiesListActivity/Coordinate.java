package com.backbase.android.weatherapp.CitiesListActivity;


/**
 * Created by Sam on 27/04/2019.
 * DTO representing Coordinate object
 */

public class Coordinate
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
