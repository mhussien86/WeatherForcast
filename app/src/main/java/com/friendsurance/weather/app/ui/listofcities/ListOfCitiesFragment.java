package com.friendsurance.weather.app.ui.listofcities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.friendsurance.weather.app.R;
import com.friendsurance.weather.app.models.City;
import com.friendsurance.weather.app.ui.OnCitySelectedListner;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 07/02/17.
 */
public class ListOfCitiesFragment extends Fragment implements ListOfCitiesAdapter.OnListItemSelected {



    OnCitySelectedListner onCitySelectedListner ;
    ButterKnife binder ;

    @Bind(R.id.cardList)
    RecyclerView citiesList ;

    ArrayList<City> arrayList ;
    ListOfCitiesAdapter citiesAdapter ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onCitySelectedListner = (OnCitySelectedListner) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        citiesList.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList.add(new City("Jeddah", true));
        arrayList.add(new City("Cairo", false));
        arrayList.add(new City("Dublin", false));
        arrayList.add(new City("London", false));
        arrayList.add(new City("Barcelona", false));
        citiesAdapter = new ListOfCitiesAdapter(getActivity(), arrayList,this);
        citiesList.setHasFixedSize(true);
        citiesList.setAdapter(citiesAdapter);

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(onCitySelectedListner!=null)onCitySelectedListner.onCityChanged(arrayList.get(0).getCityName());
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cities,container,false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.add_city));
        binder.bind(this,rootView);
        return rootView ;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_city, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_add){
            LayoutInflater li = LayoutInflater.from(getActivity());
            final View dialogView = li.inflate(R.layout.dialog_custom_view, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);
            builder.setTitle(getString(R.string.add_city));
            builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    EditText cityEditText = (EditText) dialogView.findViewById(R.id.cityEditText);
                    if(!TextUtils.isEmpty(cityEditText.getText())){
                        arrayList.add(new City(cityEditText.getText().toString(),false));
                        citiesAdapter.notifyDataSetChanged();
                    }

                }
            });
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create();
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        binder.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void selectedItem(City city) {
        for(int i = 0 ; i < arrayList.size(); i++){
            arrayList.get(i).setSelected(false);
        }
        city.setSelected(true);
        onCitySelectedListner.onCityChanged(city.getCityName());
        citiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(City city) {
        arrayList.remove(city);
        citiesAdapter.notifyDataSetChanged();
    }
}
