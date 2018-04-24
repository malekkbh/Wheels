package com.malekk.newdriver.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Malekk on 19/08/2017.
 */

public class Student implements Parcelable {

    String displayName, payment , phone ;
    int classes ;
    double debt ;

    public Student() {
    }

    public Student(String displayName, String payment, String phone, int classes, double debt) {
        this.displayName = displayName;
        this.payment = payment;
        this.phone = phone;
        this.classes = classes;
        this.debt = debt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public double getDebt() {
        return debt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "displayName='" + displayName + '\'' +
                ", payment='" + payment + '\'' +
                ", phone='" + phone + '\'' +
                ", classes=" + classes +
                ", debt=" + debt +
                '}';
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeString(this.payment);
        dest.writeString(this.phone);
        dest.writeInt(this.classes);
        dest.writeDouble(this.debt);
    }

    protected Student(Parcel in) {
        this.displayName = in.readString();
        this.payment = in.readString();
        this.phone = in.readString();
        this.classes = in.readInt();
        this.debt = in.readDouble();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
