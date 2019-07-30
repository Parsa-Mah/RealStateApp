package com.example.realstate.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private int id;
    private String title , description , avatarPath;

    public Location(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Location(String title, String description, String avatarPath) {
        this.title = title;
        this.description = description;
        this.avatarPath = avatarPath;
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


    }
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];

        }
    };
    private Location(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        avatarPath = in.readString();
    }
}
