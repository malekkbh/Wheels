package com.malekk.newdriver;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malekk.newdriver.Recycler.MyStudentsRecyclerAdapter;
import com.malekk.newdriver.Recycler.TeachersRecyclerAdapter;


public class MyStudents extends Fragment {

    RecyclerView msrv ;

    public MyStudents() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_students, container, false);

        msrv =  (RecyclerView) v.findViewById(R.id.msrv) ;
        msrv.setAdapter(new MyStudentsRecyclerAdapter(this.getContext()));
        msrv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return v ;
    }

}
