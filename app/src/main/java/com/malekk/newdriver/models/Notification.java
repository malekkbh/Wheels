package com.malekk.newdriver.models;

/**
 * Created by malekkbh on 29/12/2017.
 */

public class Notification {

    String sendTo ;
    String title  ;
    String text   ;


    public Notification() {
    }

    public Notification(String sendTo, String title, String text) {
        this.sendTo = sendTo;
        this.title = title;
        this.text = text;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
