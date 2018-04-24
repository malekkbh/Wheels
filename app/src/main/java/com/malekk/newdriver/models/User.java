package com.malekk.newdriver.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Malekk on 10/08/2017.
 */

public class User{

    String name, email;

    //Constructors
    public User() {
    }

    public User (FirebaseUser user) {
        this.name = user.getDisplayName();
        this.email = user.getEmail();
    }

    //Getters n Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //tostring

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

