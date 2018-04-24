package com.malekk.newdriver.Recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.DataSorce.TeachersDataSorce;
import com.malekk.newdriver.models.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import layout.StudentDeal;
import layout.Teachers;

import static com.malekk.newdriver.R.array.catigories;

/**
 * Created by Malekk on 01/09/2017.
 */

public class TeachersRecyclerAdapter extends RecyclerView.Adapter<TeachersRecyclerAdapter.TeacherViewHolder>   {

    private  Context context;
    List<Profile> profiles = new ArrayList<>();
    private LayoutInflater inflater;

    Context applicationContext = MainActivity.getContextOfApplication();


    SharedPreferences ref = applicationContext.getSharedPreferences("USER_INFO" , 0) ;
    SharedPreferences.Editor editor = ref.edit();



//    private  List getProfiles() {
//
//        FirebaseDatabase.getInstance().getReference().child("profile").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    PersonalProfile profile = child.getValue(PersonalProfile.class);
//                    if(profile.getTeacherStudent().equals("Teacher")){
//                        profiles.add(profile);
//                    }
//
//                }
//
//                //load the teachersList from the DB
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(context, "no data ! ", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return this.profiles ;
//    }



    //Constructor that takes the inflater.
    public TeachersRecyclerAdapter(Context context) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);

         TeachersDataSorce.getProfiles(new TeachersDataSorce.ProfileDataArrived() {
            @Override
            public void data(List<Profile> data) {
                TeachersRecyclerAdapter.this.profiles = data;
                TeachersRecyclerAdapter.this.notifyDataSetChanged();

            }
        }) ;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.teacher, parent, false);
        TeacherViewHolder holder = new TeacherViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder h, int position) {

        Profile profile = profiles.get(position);

        h.tvName.setText(profile.getName().toString());

        if ( profile.getName() != null )
         //   h.tvName.setText("");
       // else
        h.tvName.setText(profile.getName());

        if(profile.getVehicleCategories() != null)
          //  h.tvCatigories.setText("");
      //  else
        h.tvCatigories.setText(profile.getVehicleCategories());

        if(profile.getTeachingSchool() != null)
          //  h.tvSchool.setText("");
      //  else
        h.tvSchool.setText(profile.getTeachingSchool());

        if(profile.getGear() != null)
          //  h.tvGear.setText("");
      //  else
            h.tvGear.setText(profile.getGear());

        if (!profile.getImgUri().equals("") ) {
            Picasso.get().load(profile.getImgUri()).into(h.ivImg);

        }

        h.rbRating.setRating((float) profile.getRating());
      //  h.ivImg.setImageResource(Integer.parseInt(profile.getImgUri()));
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }


    public class TeacherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvGear , tvCatigories , tvSchool ;
        ImageView ivImg;
        RatingBar rbRating ;
        TextView tvName ;

        //Constructor that matches super:
        public TeacherViewHolder(View v) {
            super(v);
            tvGear = (TextView) v.findViewById(R.id.tvGear);
            tvCatigories = (TextView) v.findViewById(R.id.tvCatigories);
            tvSchool = (TextView) v.findViewById(R.id.tvSchool);
            tvName = (TextView) v.findViewById(R.id.tvName);
            ivImg = (ImageView) v.findViewById(R.id.ivImg);
            rbRating = (RatingBar) v.findViewById(R.id.rbRating);




            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            
            if ( ref.getString(MainActivity.USER_STUDENT_TEACHER , "").equals("Teacher")){
                Toast.makeText(context, "You Are a Teacher!", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

            int position = getAdapterPosition();
            Profile selectedTeacher = profiles.get(position);

            DatabaseReference refVehicleCategories = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid());
            refVehicleCategories.child("teacher").setValue(selectedTeacher.getuID());
            refVehicleCategories.child("stage").setValue(MainActivity.STUDENT_DEAL) ;



            StudentDeal studentDeal = new StudentDeal();
            Context fContext = studentDeal.getContext();

            editor.putInt(MainActivity.USER_STAGE , MainActivity.STUDENT_DEAL) ;
            editor.apply();

            FragmentActivity activity = (FragmentActivity) context;


//            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
//            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//            fragmentTransaction.add(R.id.container, studentDeal);
//            fragmentTransaction.commit();

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new StudentDeal()).commit() ;



        }//onClick
    }//teacherViewHolder

}
