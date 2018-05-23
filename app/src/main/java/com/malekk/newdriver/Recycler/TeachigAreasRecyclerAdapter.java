package com.malekk.newdriver.Recycler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
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

                final int pos = this.getAdapterPosition() ;
                final City thisCity = cityList.get(pos) ;
            AlertDialog.Builder builder = new AlertDialog.Builder(context) ;
            builder.setTitle("Detlet '" + cityList.get(pos).toString() + "' From your Teaching Areas ?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final ProgressDialog pd = new ProgressDialog(context);
                            pd.show();
                            FirebaseDatabase.getInstance().getReference().child("TeachingAreas")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(thisCity.getCity() + " - " + thisCity.getCityLocation()).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            pd.dismiss();
                                        }
                                    }) ;
                           // Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                            cityList.remove(pos) ;
                            TeachigAreasRecyclerAdapter.this.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel" , null) ;
            builder.show().show();


        }
    }
}
