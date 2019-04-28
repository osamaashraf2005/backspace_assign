package com.backbase.android.weatherapp.CitiesListActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.backbase.android.weatherapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sam on 27/04/2019.
 * CitiesAdapter for binding the data and providing filters
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.MyViewHolder> implements Filterable
{
    private List<CityInfo> citiesList;
    private List<CityInfo> citiesListFiltered;
    private CitiesAdapterListener listener;

    private TreeMap<String, CityInfo> citiesTreeMap;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, coordinates;
        public Button btnInfo;

        public MyViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.name);
            btnInfo = view.findViewById(R.id.btnInfo);

            coordinates = view.findViewById(R.id.coordinates);

            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // send selected city in callback
                    listener.onCitySelected(citiesListFiltered.get(getAdapterPosition()));
                }
            });

            btnInfo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onCityInfoSelected(citiesListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public CitiesAdapter(List<CityInfo> citiesList, TreeMap<String, CityInfo> citiesTreeMap, CitiesAdapterListener listener)
    {
        this.listener = listener;
        this.citiesList = citiesList;
        this.citiesTreeMap = citiesTreeMap;
        this.citiesListFiltered = citiesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        final CityInfo cityInfo = citiesListFiltered.get(position);
        holder.name.setText(String.valueOf(cityInfo.getCityName() + ", " + cityInfo.getCountryName()));
        holder.coordinates.setText(cityInfo.getCoordinate().toString());
    }

    @Override
    public int getItemCount()
    {
        return citiesListFiltered.size();
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                    citiesListFiltered = citiesList;
                else
                {
                    /* Using subMap method for using the benefit of O(LogN) complexity for search */
                    SortedMap<String, CityInfo> subMap = citiesTreeMap.subMap(charString, charString + Character.MAX_VALUE);

                    List<CityInfo> filteredList = new ArrayList<>();

                    for (Map.Entry<String, CityInfo> entry : subMap.entrySet())
                        filteredList.add(entry.getValue());

                    citiesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = citiesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                citiesListFiltered = (ArrayList<CityInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CitiesAdapterListener
    {
        void onCitySelected(CityInfo cityInfo);

        void onCityInfoSelected(CityInfo cityInfo);
    }
}
