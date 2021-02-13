package com.example.nf.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Content extends RealmObject implements Parcelable {

    @PrimaryKey
    public String id;
    public String webTitle;
    public String sectionName;
    public Field fields;
    public boolean pinned;
    public boolean saved;

    public Content() {
    }

    protected Content(Parcel in) {
        id = in.readString();
        webTitle = in.readString();
        sectionName = in.readString();
        pinned = in.readByte() != 0;
        saved = in.readByte() != 0;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(webTitle);
        dest.writeString(sectionName);
        dest.writeByte((byte) (pinned ? 1 : 0));
        dest.writeByte((byte) (saved ? 1 : 0));
    }
}
