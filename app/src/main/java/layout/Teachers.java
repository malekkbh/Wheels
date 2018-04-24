package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malekk.newdriver.R;
import com.malekk.newdriver.Recycler.TeachersRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Teachers extends Fragment {


     RecyclerView rvTeachers;

    public Teachers() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teachers, container, false);

        rvTeachers = (RecyclerView) v.findViewById(R.id.rvTeachers);
        rvTeachers.setAdapter(new TeachersRecyclerAdapter(this.getContext()));
        rvTeachers.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return  v ;
    }

}
