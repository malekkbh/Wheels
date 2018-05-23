package com.malekk.newdriver.models;

/**
 * Created by Malekk on 29/09/2017.
 */

public class Schedule {

    ScheduleDay sunday , monday , tuesday , wednesday, thursday ,friday , saturday ;

    public Schedule(ScheduleDay sunday, ScheduleDay monday, ScheduleDay tuesday, ScheduleDay wednesday, ScheduleDay thursday,
                    ScheduleDay friday, ScheduleDay saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public Schedule() {
    }

    public ScheduleDay getSunday() {
        return sunday;
    }

    public void setSunday(ScheduleDay sunday) {
        this.sunday = sunday;
    }

    public ScheduleDay getMonday() {
        return monday;
    }

    public void setMonday(ScheduleDay monday) {
        this.monday = monday;
    }

    public ScheduleDay getTuesday() {
        return tuesday;
    }

    public void setTuesday(ScheduleDay tuesday) {
        this.tuesday = tuesday;
    }

    public ScheduleDay getWednesday() {
        return wednesday;
    }

    public void setWednesday(ScheduleDay wednesday) {
        this.wednesday = wednesday;
    }

    public ScheduleDay getThursday() {
        return thursday;
    }

    public void setThursday(ScheduleDay thursday) {
        this.thursday = thursday;
    }

    public ScheduleDay getFriday() {
        return friday;
    }

    public void setFriday(ScheduleDay friday) {
        this.friday = friday;
    }

    public ScheduleDay getSaturday() {
        return saturday;
    }

    public void setSaturday(ScheduleDay saturday) {
        this.saturday = saturday;
    }
}
