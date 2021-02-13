package com.example.nf.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Field extends RealmObject implements Parcelable {

    public static final String PLACEHOLDER = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQPVKWgEkASAgZxIKhPDoc-F-fQLHXXJ55BXdP2Vphcf4cRGSR";

    public String thumbnail;
    public String body;

    public Field() {
    }

    protected Field(Parcel in) {
        thumbnail = in.readString();
        body = in.readString();
    }

    public static final Creator<Field> CREATOR = new Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel in) {
            return new Field(in);
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeString(body);
    }
}
