package com.malekk.newdriver.models;

/**
 * Created by malekkbh on 14/12/2017.
 */

public class lesson {

    ScheduleDay singleLesson ;

    String studentName ;
    String id ;

    public lesson(ScheduleDay singleLesson) {
        this.singleLesson = singleLesson;
        this.studentName = "free" ;
        this.id = " " ;
    }

    public lesson() {
    }

    public ScheduleDay getSingleLesson() {
        return singleLesson;
    }

    public void setSingleLesson(ScheduleDay singleLesson) {
        this.singleLesson = singleLesson;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  singleLesson.toString() +
                " - " + studentName  ;
    }
}
