package layout;


import android.app.Notification;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.util.* ;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.Schedule;
import com.malekk.newdriver.models.ScheduleDay;
import org.joda.time.LocalDateTime;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSchedule extends Fragment implements View.OnClickListener {

    TextView tvMonDAy, tvSunDay, tvTusDay, tvWeDay, tvThuDay, tvFrDay , tvSatDay, tcSunStart,  tcSunEnd, tcMonStart, tcMonEnd , tcTuStart , tcTuEnd ,
            tcWeStart , tcWeEnd , tcThuStart , tcThuEnd , tcFriStart , tcFriEnd , tcSatStart , tcSatEnd ;

    Button btnSetAll , btnSave ;

    WeekView mWeekView ;

    int minutes, hour , i=0 ;
    boolean isTimePickerUsed = false ;

    public TeacherSchedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teaher_schedule, container, false);



// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.


// Set long press listener for events.



        tcSunStart = (TextView) v.findViewById(R.id.tcSunStart) ;
        tcSunEnd   = (TextView) v.findViewById(R.id.tcSunEnd) ;

        tcMonStart = (TextView) v.findViewById(R.id.tcMonStart) ;
        tcMonEnd   = (TextView) v.findViewById(R.id.tcMonEnd) ;

        tcTuStart  = (TextView) v.findViewById(R.id.tcTuStart) ;
        tcTuEnd    = (TextView) v.findViewById(R.id.tcTuEnd) ;

        tcWeStart  = (TextView) v.findViewById(R.id.tcWeStart) ;
        tcWeEnd    = (TextView) v.findViewById(R.id.tcWeEnd) ;

        tcThuStart = (TextView) v.findViewById(R.id.tcThuStart) ;
        tcThuEnd   = (TextView) v.findViewById(R.id.tcThuEnd) ;

        tcFriStart = (TextView) v.findViewById(R.id.tcFriStart) ;
        tcFriEnd   = (TextView) v.findViewById(R.id.tcFriEnd) ;

        tcSatStart = (TextView) v.findViewById(R.id.tcSatStart) ;
        tcSatEnd   = (TextView) v.findViewById(R.id.tcSatEnd) ;

        btnSetAll  = (Button) v.findViewById(R.id.btnSetAll) ;
        btnSave    = (Button) v.findViewById(R.id.btnSave) ;




//        tcSunStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setSingleTimeZone(v);
//            }
//        });
//
//        tcSunEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setSingleTimeZone(v);
//            }
//        });

        final ProgressDialog pd = new ProgressDialog(getActivity()) ;
        pd.show();

        FirebaseDatabase.getInstance().getReference().child("teacherSchedule").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Schedule sch = null;
                        if (dataSnapshot.exists()){
                            sch = dataSnapshot.getValue(Schedule.class);
                        }

                        fillTP(sch);

                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        tcSunStart.setOnClickListener(this);
        tcSunEnd.setOnClickListener(this);
        tcMonStart.setOnClickListener(this);
        tcMonEnd.setOnClickListener(this);
        tcTuStart.setOnClickListener(this);
        tcTuEnd.setOnClickListener(this);
        tcWeStart.setOnClickListener(this);
        tcWeEnd.setOnClickListener(this);
        tcThuStart.setOnClickListener(this);
        tcThuEnd.setOnClickListener(this);
        tcFriStart.setOnClickListener(this);
        tcFriEnd.setOnClickListener(this);
        tcSatStart.setOnClickListener(this);
        tcSatEnd.setOnClickListener(this);





        btnSetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerForSetAll();
            }
        });


        return v;
    }

    private void save() {

        Schedule teacherSchedule = new Schedule(
                new ScheduleDay(tcSunStart.getText().toString(), tcSunEnd.getText().toString()),
                new ScheduleDay(tcMonStart.getText().toString(), tcMonEnd.getText().toString()),
                new ScheduleDay(tcTuStart.getText().toString(), tcTuEnd.getText().toString()),
                new ScheduleDay(tcWeStart.getText().toString(), tcWeEnd.getText().toString()),
                new ScheduleDay(tcThuStart.getText().toString(), tcThuEnd.getText().toString()),
                new ScheduleDay(tcFriStart.getText().toString(), tcFriEnd.getText().toString()),
                new ScheduleDay(tcSatStart.getText().toString(), tcSatEnd.getText().toString())
        );




        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("teacherSchedule").child(mUser.getUid());
        ref.setValue(teacherSchedule);



    }

    public void timePickerForSetAll() {
//
//        new TimePickerDialog.Builder(getActivity())
//                .setTitle("this is title")
//                .show();

            i=0;
            TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String m , h ;

                    if(minute < 10 )
                        m = "0"+minute ;
                    else
                        m = minute+"" ;

                    if(hourOfDay<10)
                        h="0"+hourOfDay ;
                    else
                        h=hourOfDay+"";

                        tcSunEnd.setText(h + ":" + m);
                        tcMonEnd.setText(h + ":" + m);
                        tcTuEnd.setText (h + ":" + m);
                        tcWeEnd.setText (h + ":" + m);
                        tcThuEnd.setText(h + ":" + m);
                        tcFriEnd.setText(h + ":" + m);
                        tcSatEnd.setText(h + ":" + m);

                        save();

                }
            }, 0, 0, true);

            dialog.show();

        if (i==0){
            i++ ;
            TimePickerDialog dialogEnd = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String m , h ;

                    if(minute < 10 )
                        m = "0"+minute ;
                    else
                        m = minute+"" ;

                    if(hourOfDay<10)
                        h="0"+hourOfDay ;
                    else
                        h=hourOfDay+"";

                        tcSunStart.setText(h + ":" + m);
                        tcMonStart.setText(h + ":" + m);
                        tcTuStart.setText (h + ":" + m);
                        tcWeStart.setText (h + ":" + m);
                        tcThuStart.setText(h + ":" + m);
                        tcFriStart.setText(h + ":" + m);
                        tcSatStart.setText(h + ":" + m);

                        save();
                }
            }, 0, 0, true);

            dialogEnd.show();
        }//if

        LocalDateTime q = new LocalDateTime ();
        String s = q.toString("dd.MM.yy");

        save();

        Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();

    }


    public void fillTP (Schedule schedule) {


        tcSunStart.setText(schedule.getSunday().getStart().toString());
        tcSunEnd  .setText(schedule.getSunday().getEnd().toString());

        tcMonStart .setText(schedule.getMonday().getStart().toString());
        tcMonEnd  .setText(schedule.getMonday().getEnd().toString());

        tcTuStart  .setText(schedule.getTuesday().getStart().toString());
        tcTuEnd    .setText(schedule.getTuesday().getEnd().toString());

        tcWeStart  .setText(schedule.getWednesday().getStart().toString());
        tcWeEnd    .setText(schedule.getWednesday().getEnd().toString());

        tcThuStart .setText(schedule.getThursday().getStart().toString());
        tcThuEnd   .setText(schedule.getThursday().getEnd().toString());

        tcFriStart .setText(schedule.getFriday().getStart().toString());
        tcFriEnd   .setText(schedule.getFriday().getEnd().toString());

        tcSatStart .setText(schedule.getSaturday().getStart().toString());
        tcSatEnd   .setText(schedule.getSaturday().getEnd().toString());

    }




    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }



    public void setSingleTimeZone (final View v) {

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

        final TextView tv = (TextView) v ;

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String m , h ;

                if(minute < 10 )
                    m = "0"+minute ;
                else
                    m = minute+"" ;

                if(hourOfDay<10)
                    h="0"+hourOfDay ;
                else
                    h=hourOfDay+"";

                tv.setText(h + ":" + m);

                save();

            }
        }, 0, 0, true);

        dialog.show();



            }// setSingTimeZone




    @Override
    public void onClick(View v) {
        setSingleTimeZone(v);
    }


}