package com.example.forhad.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.activity.wetherlist_activity.RecyclerItemClickListener;
import com.example.forhad.weatherapp.http.apimodel.WeatherList;
import com.example.forhad.weatherapp.utils.UtilMethods;

import java.util.ArrayList;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.EmployeeViewHolder> {

    private ArrayList<WeatherList> dataList;
    private RecyclerItemClickListener recyclerItemClickListener;

    UtilMethods utilMethods;

    public WeatherListAdapter(ArrayList<WeatherList> dataList , RecyclerItemClickListener recyclerItemClickListener) {
        this.dataList = dataList;
        this.recyclerItemClickListener = recyclerItemClickListener;

        utilMethods = new UtilMethods();
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.weather_list_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.cityName.setText(dataList.get(position).getName());
        holder.weatherStatus.setText(utilMethods.capFirstLetter(dataList.get(position).getWeather().get(0).getDescription()));

        double tempInDouble = dataList.get(position).getMain().getTemp();



        holder.temp.setText(utilMethods.getTempdataInCalcious(tempInDouble));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(dataList.get(position));


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView cityName, temp, weatherStatus;


        EmployeeViewHolder(View itemView) {
            super(itemView);
            cityName =  itemView.findViewById(R.id.cityName);
            temp =  itemView.findViewById(R.id.temp);
            weatherStatus =  itemView.findViewById(R.id.weatherStatus);

        }
    }
}