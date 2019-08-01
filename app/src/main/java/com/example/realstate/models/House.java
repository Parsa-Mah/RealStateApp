package com.example.realstate.models;

import android.os.Parcel;
import android.os.Parcelable;

public class House implements Parcelable {
    private int id;
    private String title , description , avatarPath , coordination;

    public House(int Id, String title, String description, String avatarPath) {
        this.title = title;
        this.description = description;
        this.avatarPath = avatarPath;
    }

    public House(int id, String title, String description, String avatarPath, String coordination) {
        this(id , title , description , avatarPath);
        this.coordination = coordination;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoordination() {
        return coordination;
    }

    public void setCoordination(String coordination) {
        this.coordination = coordination;
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
        dest.writeString(avatarPath);
        dest.writeString(coordination);


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
        avatarPath = in.readString();
        coordination = in.readString();
    }
}
