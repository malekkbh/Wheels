package layout;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.R;
import com.malekk.newdriver.Recycler.TeacherDayRecyclerAdapter;
import com.malekk.newdriver.models.Day;
import com.malekk.newdriver.models.ScheduleDay;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherDay extends Fragment implements  DatePickerDialog.OnDateSetListener{

    LocalDateTime today  ;
    FloatingActionButton fabCal ;
    List<ScheduleDay> dayClasses ;
    public static    RecyclerView vr ;
    Context context = getContext() ;

    public TeacherDay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teacher_day, container, false);

        today = new LocalDateTime();

        fabCal = (FloatingActionButton) v.findViewById(R.id.fabCal) ;

        fabCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dateDialog = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        today = new LocalDateTime(year , month +1 , dayOfMonth , 0,0,0) ;

                        vr.setAdapter(new TeacherDayRecyclerAdapter(TeacherDay.this.getContext() , today ));
                        vr.setLayoutManager(new LinearLayoutManager(TeacherDay.this.getContext()  ));

                        Toast.makeText(getContext(), today.toString() , Toast.LENGTH_LONG ).show();

                    }
                }
                        ,today.getYear() , today.getMonthOfYear() -1  , today.getDayOfMonth()) ;


                    dateDialog.show();
            }
        });

        vr = (RecyclerView) v.findViewById(R.id.rvTeacherDay);
        vr.setAdapter(new TeacherDayRecyclerAdapter(getActivity(), today)) ;
        vr.setLayoutManager(new LinearLayoutManager(this.getContext()));
        System.out.println("onCreate - Fragment - adapter");


        return v ;
    }// onCreat

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

//    public void getTeacherDay(final getTeacherDay listener){
//
//        String teacherUID = "" , date = "";
//
//        FirebaseDatabase.getInstance().getReference().child("Day").child(teacherUID).child(date)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        List<ScheduleDay> day = new ArrayList<>();
//
//                        for (DataSnapshot child : dataSnapshot.getChildren()) {
//                            ScheduleDay classs  = child.getValue(ScheduleDay.class);
//                            day.add(classs);
//                        }
//                        listener.data(day);
//                        //load the teachersList from the DB
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                        Toast.makeText(  getActivity() , "noData", Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                    });
//                }


}
