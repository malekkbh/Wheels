package com.malekk.newdriver.DataSorce;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.ScheduleDay;
import com.malekk.newdriver.models.lesson;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import layout.TeacherSchedule;

import static android.app.PendingIntent.getActivity;

/**
 * Created by malekkbh on 07/12/2017.
 */

public class TeacherDayDataSorce extends Application {

   static FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser() ;

    static LocalDateTime dayDate ;
    static String dayText ;
    static String studentTeacher ;
    static String teacherID ;
    static Context context ;
    int c ;


    Context applicationContext = MainActivity.getContextOfApplication();
    SharedPreferences ref = applicationContext.getSharedPreferences("USER_INFO" , 0) ;
    String StudentName = ref.getString("Name" , "") ;


    //con'
    public TeacherDayDataSorce ( LocalDateTime dayDate){


        this.dayDate = dayDate ;
        dayText = dayDate.dayOfWeek().getAsText(new Locale("en")).toLowerCase() ;

        this.studentTeacher = ref.getString("StudentTeacher" , "");
        this.teacherID = ref.getString("Teacher" , "") ;

        this.c = 0 ;

    }



    public interface TeacherDayDataArrived {
        void data(List<lesson> data);
    }

   public interface DayOfTheWeekArrived {
        void data ( ScheduleDay dayData);
   }

    public void getTeacherDay(final TeacherDayDataArrived listener) {
        System.out.println("Get Teacher Day" +  teacherID);

       DatabaseReference reff = FirebaseDatabase.getInstance().getReference()
                .child("Day")
                .child(teacherID)
                //.child(dayDate.toString("yy/MM/dd"))
                .child(String.valueOf(dayDate.year().get()))
                .child(String.valueOf(dayDate.getMonthOfYear()))
                .child(String.valueOf(dayDate.getDayOfMonth())) ;

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<lesson> classes = new ArrayList<>();

                if(studentTeacher.equals("Student")){
                     for (DataSnapshot child : dataSnapshot.getChildren()) {

                         System.out.println("************Found data"+child);

                         lesson clas = child.getValue(lesson.class);
                         System.out.println("child: " + clas.toString());
                         if(clas.getStudentName().equals("free"))
                             classes.add(clas);
                         else {
                             if( clas.getStudentName().equals(TeacherDayDataSorce.this.StudentName))
                                 classes.add(clas) ;
                             else {
                                 clas.setStudentName("TAken!");
                                 classes.add(clas);
                             }
                         }
                     }

                }//ifStudent
                else {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        lesson clas = child.getValue(lesson.class);
                        classes.add(clas);
                    }
                }//else (Tacher)

                listener.data(classes);
                //load the teachersList from the DB
            }//onDataChange
            @Override
            public void onCancelled(DatabaseError e) {
                System.out.println("***************Failed - code: "+e.getCode()+" details "+ e.getDetails());

            }//onCancelled
        });//addListenerForSingleValueEvent

    }//getTeacher

    public static void getDayOfTheWeek (Context context1 , final DayOfTheWeekArrived dayListener ){
        context = context1 ;
        FirebaseDatabase.getInstance().getReference().child("teacherSchedule").child(teacherID).child(dayText).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            ScheduleDay newDay = dataSnapshot.getValue(ScheduleDay.class);
                            dayListener.data(newDay);
                        }

                        else dayListener.data(null);
//                        else{
//
//                            String message , title ;
//
//
//
//                            if (studentTeacher.equals("Student")) {
//                                message = "Please contact Your teacher" ;
//                                title = "No Schedul for This Day !" ;
//                            }
//                            else
//                            {
//                                message = "Please edit your Schedule , press 'OK' to edit " ;
//                                title = "No Schedul for This Day !" ;
//                            }
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                            builder.setMessage(message)
//                                    .setTitle(title)
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface d, int which) {
//                                            if (studentTeacher.equals("Teacher") ) {
//                                                FragmentActivity activity = (FragmentActivity) context;
//                                                activity.getSupportFragmentManager().beginTransaction()
//                                                        .replace(R.id.container, new TeacherSchedule()).commit();
//                                                // dialogNoSchedule.dismiss();
//                                                // ((FragmentActivity) context).finish();
//                                                d.dismiss();
//                                            }
//                                        }
//                                    });
//                            final AlertDialog dialogNoSchedule = builder.show();
//
//                            dialogNoSchedule.show();
//
//                            return;
//
//
//
//
//                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }




    public void noSchedulDialog() {

       final String teacherStudent = applicationContext.getSharedPreferences("USER_INFO" , 0).getString(MainActivity.USER_STUDENT_TEACHER , "") ;
        String message , title ;



        if (teacherStudent.equals("Student")) {
            message = "Please contact Your teacher" ;
            title = "No Schedul for This Day !" ;
        }
        else
        {
            message = "Please edit your Schedule , press 'OK' to edit " ;
            title = "No Schedul for This Day !" ;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (teacherStudent.equals("Teacher")) {
                            FragmentActivity activity = (FragmentActivity) context;
                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new TeacherSchedule()).commit();
                            // dialogNoSchedule.dismiss();
                            // ((FragmentActivity) context).finish();
                        }
                    }
                });
        final AlertDialog dialogNoSchedule = builder.show();

        dialogNoSchedule.show();

    }



}
