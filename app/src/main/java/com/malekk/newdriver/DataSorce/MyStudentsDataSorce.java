package com.malekk.newdriver.DataSorce;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.models.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malekkbh on 10/05/2018.
 */

public class MyStudentsDataSorce {

    public interface ProfileDataArrived {
        void data(List<Profile> data);
    }

    public static void getProfiles(final TeachersDataSorce.ProfileDataArrived listener) {
        FirebaseDatabase.getInstance().getReference()
                .child("profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Profile> profiles = new ArrayList<>();

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Profile profile = child.getValue(Profile.class);

                            if(profile.getTeacher() != null)
                                if(profile.getTeacher().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
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
