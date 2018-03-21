package com.the_movie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class ModelGenres implements Parcelable {
    int id;
    String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public ModelGenres() {
    }

    protected ModelGenres(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ModelGenres> CREATOR = new Parcelable.Creator<ModelGenres>() {
        @Override
        public ModelGenres createFromParcel(Parcel source) {
            return new ModelGenres(source);
        }

        @Override
        public ModelGenres[] newArray(int size) {
            return new ModelGenres[size];
        }
    };
}
