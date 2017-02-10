package com.friendsurance.weather.app.ui.listofcities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.friendsurance.weather.app.R;
import com.friendsurance.weather.app.models.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 09/02/17.
 */
public class ListOfCitiesAdapter extends RecyclerView.Adapter<ListOfCitiesAdapter.ViewHolder> {

    private List<City> cityList = new ArrayList<>();
    private Context context ;
    OnListItemSelected onListItemSelected ;

    public ListOfCitiesAdapter(Context context, ArrayList<City> cityList,OnListItemSelected onListItemSelected) {
        this.cityList = cityList;
        this.context = context ;
        this.onListItemSelected = onListItemSelected ;
    }
    public interface OnListItemSelected{
        void selectedItem(City city);
        void deleteItem(City city);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListOfCitiesAdapter.ViewHolder holder, int position) {

        holder.radioButton.setChecked(cityList.get(position).isSelected());
        holder.bind(cityList.get(position), onListItemSelected);
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cityName)
        TextView cityName ;

        @Bind(R.id.deleteButton)
        ImageButton deleteButton ;

        @Bind(R.id.radioButton)
        RadioButton radioButton ;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final City city, final OnListItemSelected listener) {

            cityName.setText(city.getCityName());
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.deleteItem(city);
                }
            });
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.selectedItem(city);
                }
            });
        }
    }
}
