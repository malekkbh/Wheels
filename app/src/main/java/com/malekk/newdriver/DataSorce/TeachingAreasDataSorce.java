package com.malekk.newdriver.DataSorce;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.models.City;
import com.malekk.newdriver.models.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malekk on 10/09/2017.
 */

public class TeachingAreasDataSorce {

   static FirebaseAuth mAuth = FirebaseAuth.getInstance() ;
    static FirebaseUser mUser = mAuth.getCurrentUser() ;
    static String uID = mUser.getUid();


    public interface CityDataArrived {
        void data(List<City> data);
    }

    public static void getAreas(final CityDataArrived listener) {
        FirebaseDatabase.getInstance().getReference().child("TeachingAreas").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<City> cityList = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    City city = child.getValue(City.class);
                        cityList.add(city);
                }
                listener.data(cityList);
                //load the teachersList from the DB
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(co, "", Toast.LENGTH_SHORT).show();
            }
        });

    }


}


