package com.example.realstate.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class House implements Parcelable {
    private int id;
    private String title, description;
    private String avatar;
    private double latitude;
    private double longitude;
    private LatLng coordination;

    public House() {
    }


    public House(int id, String title, String description, String avatar, double latitude, double longitude) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.avatar = avatar;
        this.latitude = latitude;
        this.longitude = longitude;
        coordination = new LatLng(latitude, longitude);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getCoordination() {
        return coordination;
    }

    public void setCoordination(LatLng coordination) {
        this.coordination = coordination;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(avatar);
        dest.writeParcelable(coordination, i);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);


    }

    public static final Parcelable.Creator<House> CREATOR = new Parcelable.Creator<House>() {
        public House createFromParcel(Parcel in) {
            return new House(in);
        }

        public House[] newArray(int size) {
            return new House[size];

        }
    };

    private House(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        avatar = in.readString();
        coordination = in.readParcelable(getClass().getClassLoader());
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
}
