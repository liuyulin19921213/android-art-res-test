package com.lyl.chapter_2_test.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.lyl.chapter_2_test.aidl.Book;

import java.io.Serializable;


/**
 * Created by lyl on 19-3-12
 */
public class User implements Parcelable, Serializable {


    private static final long serialVersionUID = -5525032529871849278L;

    public int userId;
    public String userName;
    public boolean isMale;

    public Book book;


    public User() {
    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.book, flags);
    }

    protected User(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.isMale = in.readByte() != 0;
        this.book = in.readParcelable(Book.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
