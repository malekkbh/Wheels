package com.malekk.newdriver.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Malekk on 10/08/2017.
 */

public class Profile implements Parcelable {

    String name;
    String uID;
    String email;
    String phoneNumber;
    String teacherStudent;
    String payment;
    String teacher;
    String cityLocation;
    String city;
    String imgUri ;
    double balance ;
    double rating ;
    double teacherBalance ;
    int lessons ;
    int deal ;
    boolean teoryah;
    String vehicleCategories ;
    String gear ;
    String teachingSchool;
    int ratingCounter;
    String pricePerLesson ;
    String priceForGlobalDeal;
    int stage ;


    public Profile() {
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getRatingCounter() {
        return ratingCounter;
    }

    public void setRatingCounter(int ratingCounter) {
        this.ratingCounter = ratingCounter;
    }

    public String getPricePerLesson() {
        return pricePerLesson;
    }

    public void setPricePerLesson(String pricePerLesson) {
        this.pricePerLesson = pricePerLesson;
    }

    public String getPriceForGlobalDeal() {
        return priceForGlobalDeal;
    }

    public void setPriceForGlobalDeal(String priceForGlobalDeal) {
        this.priceForGlobalDeal = priceForGlobalDeal;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Profile(String name, String email, String phoneNumber, String teacherStudent, String payment,
                   String teacher, String cityLocation, String city, String imgUri, double balance, double rating,
                   double teacherBalance, int lessons, int deal, boolean teoryah , String vehicleCategories , String gear ,
                   String teachingSchool , String pricePerLesson , String priceForGlobalDeal , String uID , int stage ) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.teacherStudent = teacherStudent;
        this.payment = payment;
        this.teacher = teacher;
        this.cityLocation = cityLocation;
        this.city = city;
        this.imgUri = imgUri;
        this.balance = balance;
        this.rating = rating;
        this.teacherBalance = teacherBalance;
        this.lessons = lessons;
        this.deal = deal;
        this.teoryah = teoryah;
        this.vehicleCategories = vehicleCategories ;
        this.gear = gear ;
        this.teachingSchool = teachingSchool ;
        this.pricePerLesson = pricePerLesson ;
        this.priceForGlobalDeal = priceForGlobalDeal ;
        this.ratingCounter = 0 ;
        this.uID = uID ;

        this.stage = stage ;

    }

    @Override
    public String toString() {
        return "PersonalProfile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", teacherStudent='" + teacherStudent + '\'' +
                ", payment='" + payment + '\'' +
                ", teacher='" + teacher + '\'' +
                ", cityLocation='" + cityLocation + '\'' +
                ", city='" + city + '\'' +
                ", imgUri='" + imgUri + '\'' +
                ", balance=" + balance +
                ", rating=" + rating +
                ", teacherBalance=" + teacherBalance +
                ", lessons=" + lessons +
                ", deal=" + deal +
                ", teoryah=" + teoryah +
                '}';
    }

    public double getTeacherBalance() {
        return teacherBalance;
    }


    public String getTeachingSchool() {
        return teachingSchool;
    }

    public void setTeachingSchool(String teachingSchool) {
        this.teachingSchool = teachingSchool;
    }

    public void setTeacherBalance(double teacherBalance) {
        this.teacherBalance = teacherBalance;
    }

    public String getVehicleCategories() {
        return vehicleCategories;
    }

    public void setVehicleCategories(String vehicleCategories) {
        this.vehicleCategories = vehicleCategories;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    public static Creator<Profile> getCREATOR() {
        return CREATOR;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = this.rating* ratingCounter;
        this.ratingCounter++ ;
        this.rating = (this.rating + rating) / this.ratingCounter;
    }

    public int getDeal() {
        return deal;
    }

    public void setDeal(int deal) {
        this.deal = deal;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTeacherStudent() {
        return teacherStudent;
    }

    public void setTeacherStudent(String teacherStudent) {
        this.teacherStudent = teacherStudent;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public boolean isTeoryah() {
        return teoryah;
    }

    public void setTeoryah(boolean teoryah) {
        this.teoryah = teoryah;
    }

    public String getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getLessons() {
        return lessons;
    }

    public void setLessons(int lessons) {
        this.lessons = lessons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.teacherStudent);
        dest.writeString(this.payment);
        dest.writeString(this.teacher);
        dest.writeString(this.cityLocation);
        dest.writeString(this.city);
        dest.writeString(this.imgUri);
        dest.writeDouble(this.balance);
        dest.writeDouble(this.rating);
        dest.writeDouble(this.teacherBalance);
        dest.writeInt(this.lessons);
        dest.writeInt(this.deal);
        dest.writeByte(this.teoryah ? (byte) 1 : (byte) 0);
    }

    protected Profile(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.teacherStudent = in.readString();
        this.payment = in.readString();
        this.teacher = in.readString();
        this.cityLocation = in.readString();
        this.city = in.readString();
        this.imgUri = in.readString();
        this.balance = in.readDouble();
        this.rating = in.readDouble();
        this.teacherBalance = in.readDouble();
        this.lessons = in.readInt();
        this.deal = in.readInt();
        this.teoryah = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
