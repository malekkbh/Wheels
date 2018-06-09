package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malekk.newdriver.R;
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


        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               // Log.i(tag, "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //Log.i(tag, "onKey Back listener is working!!!");
                   // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getFragmentManager().beginTransaction().replace(R.id.container , new TeacherDay()).commit() ;
                    return true;
                }
                return false;
            }
        });


        return v ;
    }



}
