package com.malekk.newdriver.models;

/**
 * Created by Malekk on 29/09/2017.
 */

public class ScheduleDay {

    String start ;
    String end ;


    public ScheduleDay(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public ScheduleDay() {
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ScheduleDay{" +
                 start + '\'' +
                "-" + end + '\'' +
                '}';
    }
}
