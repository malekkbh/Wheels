package com.malekk.newdriver.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.malekk.newdriver.DataSorce.TeachingAreasDataSorce;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malekk on 10/09/2017.
 */

public class TeachigAreasRecyclerAdapter extends RecyclerView.Adapter<TeachigAreasRecyclerAdapter.TeachingAreasViewHolder> {


    public List<City> getCityList() {
        return cityList;
    }

    List <City> cityList = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context ;

    public TeachigAreasRecyclerAdapter( Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        TeachingAreasDataSorce.getAreas(new TeachingAreasDataSorce.CityDataArrived() {
            @Override
            public void data(List<City> data) {
                TeachigAreasRecyclerAdapter.this.cityList = data ;
                TeachigAreasRecyclerAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public TeachingAreasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.teaching_area_item , parent , false) ;

        TeachingAreasViewHolder holder = new TeachingAreasViewHolder(v) ;

        return holder ;

    }

    @Override
    public void onBindViewHolder(TeachingAreasViewHolder h, int position) {

        City city = cityList.get(position) ;
        h.tvArea.setText(city.toString());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }





    public class TeachingAreasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvArea ;

        public TeachingAreasViewHolder(View v) {
            super(v);
            tvArea = (TextView) v.findViewById(R.id.tvArea) ;

            v.setOnClickListener(this );
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();

        }
    }
}
