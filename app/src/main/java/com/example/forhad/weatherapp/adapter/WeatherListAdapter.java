package com.example.forhad.weatherapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.forhad.weatherapp.R;
import com.example.forhad.weatherapp.model.WeatherList;
import com.example.forhad.weatherapp.model.WeatherResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class WeatherListAdapter extends BaseAdapter {



	private ArrayList<WeatherList> weatherModelArrayList;



	private Context context;

	public WeatherListAdapter(Context context,
                              ArrayList<WeatherList> weatherModelArrayList) {

		// TODO Auto-generated constructor stub
		this.context = context;
		this.weatherModelArrayList = weatherModelArrayList;


	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weatherModelArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;


		if (view == null) {

			view = LayoutInflater.from(context).inflate(
					R.layout.weather_list_item, parent, false);
		}

		TextView cityName = view.findViewById(R.id.cityName);
		TextView temp = view.findViewById(R.id.temp);
		TextView weatherStatus = view.findViewById(R.id.weatherStatus);


		cityName.setText(weatherModelArrayList.get(position).getName());
		weatherStatus.setText(capFirstLetter(weatherModelArrayList.get(position).getWeather().get(0).getDescription()));



		double tempInDouble = weatherModelArrayList.get(position).getMain().getTemp();






		DecimalFormat df = new DecimalFormat("#");

		String tempString = df.format(tempInDouble);

		temp.setText(tempString + ((char) 0x00B0) + "C" );



		return view;
	}



	public String capFirstLetter(String text){
		StringBuilder sb = new StringBuilder(text);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}


}