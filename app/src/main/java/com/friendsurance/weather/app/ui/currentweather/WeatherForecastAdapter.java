package com.friendsurance.weather.app.ui.currentweather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.friendsurance.weather.app.R;
import com.friendsurance.weather.app.models.WeatherForecastListDataEnvelope;
import com.friendsurance.weather.app.utils.Constants;
import com.friendsurance.weather.app.utils.DayFormatter;
import com.friendsurance.weather.app.utils.TemperatureFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder> {

    private List<WeatherForecastListDataEnvelope.ForecastDataEnvelope> weatherList = new ArrayList<>();

    private Context context ;
    public WeatherForecastAdapter(Context context, WeatherForecastListDataEnvelope weatherList) {
        this.weatherList = weatherList.list;
        this.context = context ;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherForecastAdapter.ViewHolder holder, int position) {

        holder.dayName.setText(new DayFormatter(context).format(weatherList.get(position).timestamp));
        holder.mHighTemperature.setText(TemperatureFormatter.format(weatherList.get(position).temp.max));
        holder.mLowTemperature.setText(TemperatureFormatter.format(weatherList.get(position).temp.min));
        holder.mHumidity.setText(TemperatureFormatter.formatHumidty(weatherList.get(position).humidity));
        holder.mPressure.setText(TemperatureFormatter.formatPressure(weatherList.get(position).pressure));
        holder.mWeatherDescription.setText(weatherList.get(position).weather.get(0).description);
        Glide.with(this.context).load(Constants.WEATHER_IMAGE_URL+weatherList.get(position).weather.get(0).icon+".png").into(holder.mWeatherIcon);
    }


    @Override
    public int getItemCount() {
        return weatherList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.day_name)
        TextView dayName;
        @Bind(R.id.high_temp)
        TextView mHighTemperature;

        @Bind(R.id.low_temp)
        TextView mLowTemperature;

        @Bind(R.id.humidity)
        TextView mHumidity;

        @Bind(R.id.pressure)
        TextView mPressure;

        @Bind(R.id.weather_description)
        TextView mWeatherDescription;

        @Bind(R.id.weather_icon)
        ImageView mWeatherIcon;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }

    }
}
