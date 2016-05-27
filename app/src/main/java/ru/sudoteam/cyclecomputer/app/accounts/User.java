package ru.sudoteam.cyclecomputer.app.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bagrusss on 20.04.16.
 * *
 */

public class User implements Parcelable {
    public long id;
    public String firstName;
    public String lastName;
    public String imgURL;

    public User() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(imgURL);
    }

    public User(Parcel data) {
        id = data.readLong();
        firstName = data.readString();
        lastName = data.readString();
        imgURL = data.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
