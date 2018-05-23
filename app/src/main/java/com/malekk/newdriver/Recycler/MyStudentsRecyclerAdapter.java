package com.malekk.newdriver.Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.malekk.newdriver.DataSorce.MyStudentsDataSorce;
import com.malekk.newdriver.DataSorce.TeachersDataSorce;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import layout.StudentDeal;
import layout.TeacherDay;

/**
 * Created by malekkbh on 10/05/2018.
 */

public class MyStudentsRecyclerAdapter extends RecyclerView.Adapter<MyStudentsRecyclerAdapter.MyStudentsViewHolder> {

    private Context context;
    List<Profile> profiles = new ArrayList<>();
    private LayoutInflater inflater;

    Context applicationContext = MainActivity.getContextOfApplication();


    SharedPreferences ref = applicationContext.getSharedPreferences("re" , 0) ;
    SharedPreferences.Editor editor = ref.edit();



    //Constructor that takes the inflater.
    public MyStudentsRecyclerAdapter(Context context) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);

        MyStudentsDataSorce.getProfiles(new TeachersDataSorce.ProfileDataArrived() {
            @Override
            public void data(List<Profile> data) {
                MyStudentsRecyclerAdapter.this.profiles = data;
                MyStudentsRecyclerAdapter.this.notifyDataSetChanged();

            }
        });
    }


    @Override
    public MyStudentsRecyclerAdapter.MyStudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.teacher, parent, false);
        MyStudentsRecyclerAdapter.MyStudentsViewHolder holder = new MyStudentsRecyclerAdapter.MyStudentsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyStudentsViewHolder h, int position) {
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



    public class MyStudentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvGear, tvCatigories, tvSchool;
        ImageView ivImg;
        RatingBar rbRating;
        TextView tvName;

        //Constructor that matches super:
        public MyStudentsViewHolder(View v) {
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

            int pos = getAdapterPosition() ;
            final Profile profile = profiles.get(pos);

                if(ref.getInt("mChange" , 0) ==     1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context) ;

                    builder.setTitle("Replace With this Student?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editor.putInt("mChange" , 2) ;
                                    editor.putString("newID", profile.getuID());
                                    editor.putString("newName" , profile.getName()) ;
                                    editor.commit();

                                    FragmentActivity activity = (FragmentActivity) context;
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new TeacherDay()).commit() ;

                                }
                            })
                            .setNegativeButton("Cancell" , null).show().show(); ;



                }
        }
    }
    }
