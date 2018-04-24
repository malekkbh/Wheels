package com.malekk.newdriver.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.malekk.newdriver.MainActivity;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malekkbh on 07/12/2017.
 */

public class Day {

        ScheduleDay day ;
        int classLength = 45 ;
        LocalDateTime date ;

    Context applicationContext = MainActivity.getContextOfApplication();


    SharedPreferences preferences = applicationContext.getSharedPreferences("USER_INFO" , 0) ;

    String teacherID = preferences.getString("Teacher" , "" ) ;

        public List daySchedule ;

        public Day ( ScheduleDay day , LocalDateTime date) {

            this.day = day ;
            this.date = date ;

            makeMyDay() ;

        }


        private void makeMyDay() {

            int h,m ;


            int startH = Integer.valueOf(day.getStart().substring(0,2));
            h= startH ;
            int startM =Integer.valueOf(day.getStart().substring(3));
            m=startM ;

            int enfH =Integer.valueOf(day.getEnd().substring(0,2));
            int endM =Integer.valueOf(day.getEnd().substring(3));


            LocalDateTime endTime = new LocalDateTime(date.getYear(),date.getMonthOfYear(),date.getDayOfMonth(),enfH , endM) ;
            LocalDateTime timeNow = new LocalDateTime(date.getYear(),date.getMonthOfYear(),date.getDayOfMonth(),h ,m);

            daySchedule = new ArrayList() ;


            while ( !endTime.isBefore(timeNow)){
                if ( m + classLength < 60  ){

                    daySchedule.add(new lesson (new ScheduleDay(h + ":" + m   ,h + ":" +(m + classLength ) ) ));
                    m = m + classLength ;
                    timeNow = new LocalDateTime(date.getYear(),date.getMonthOfYear(),date.getDayOfMonth() ,h,m);
                } else if ( h+1 <=enfH  ){

                    daySchedule.add( new lesson(new ScheduleDay (h + ":" + m , (h+1) + ":" + (classLength-(60-m)))));
                    h++ ;
                    m= (classLength-(60-m));
                    timeNow = new LocalDateTime(date.getYear(),date.getMonthOfYear(),date.getDayOfMonth(),h,m);
                }

            }//while

            int ccccc = 1 ;

            for (int i = 0 ; i<daySchedule.size() ; i++ ) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Day").
                        child(teacherID)
                        .child(String.valueOf(endTime.year().get()))
                        .child(String.valueOf(endTime.getMonthOfYear()))
                        .child(String.valueOf(endTime.getDayOfMonth()))

                        .child(daySchedule.get(i).toString());
                ref.setValue(daySchedule.get(i));

                // addLessonChild(daySchedule.get(i) , endTime) ;
            }//for

        }//make my day

    public static void addLessonChild(Object les, LocalDateTime time) {

            String s = les.toString() ;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Day").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(String.valueOf(time.year().get()))
                .child(String.valueOf(time.getMonthOfYear()))
                .child(String.valueOf(time.getDayOfMonth()))

                .child(s);
        ref.setValue(les);
    }


    @Override
    public String toString() {
        return "Day{" +
                "day=" + day +
                ", classLength=" + classLength +
                ", daySchedule=" + daySchedule +
                '}';
    }
}
