package com.malekk.newdriver.DataSorce;

import android.graphics.Movie;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.models.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malekk on 01/09/2017.
 */

public class TeachersDataSorce {


    public interface ProfileDataArrived {
        void data(List<Profile> data);
    }

    public static void getProfiles(final ProfileDataArrived listener) {
        FirebaseDatabase.getInstance().getReference()
                .child("profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Profile> profiles = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Profile profile = child.getValue(Profile.class);
                        if(profile.getTeacherStudent()!= null)
                             if(profile.getTeacherStudent().equals("Teacher"))
                                   profiles.add(profile);
                }
                listener.data(profiles);
                //load the teachersList from the DB
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Toast.makeText(co, "", Toast.LENGTH_SHORT).show();
            }
        });

     }


}
