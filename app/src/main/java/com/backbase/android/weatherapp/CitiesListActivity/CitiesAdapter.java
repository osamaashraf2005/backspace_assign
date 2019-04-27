package com.backbase.android.weatherapp.CitiesListActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.backbase.android.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.MyViewHolder> implements Filterable
{
    private Context context;
    private List<CityInfo> citiesList;
    private List<CityInfo> citiesListFiltered;
    private CitiesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, coordinates;

        public MyViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.name);
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
        }
    }


    public CitiesAdapter(Context context, List<CityInfo> citiesList, CitiesAdapterListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.citiesList = citiesList;
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
                {
                    citiesListFiltered = citiesList;
                } else
                {
                    List<CityInfo> filteredList = new ArrayList<>();
                    for (CityInfo row : citiesList)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for city name
//                        if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence))
                        if (row.getCityName().toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row);
                    }

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
    }
}
