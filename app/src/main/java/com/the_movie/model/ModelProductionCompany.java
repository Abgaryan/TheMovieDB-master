package com.the_movie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class ModelProductionCompany implements Parcelable {
    int id;

    String logo_path;

    String name;

    String origin_country;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.logo_path);
        dest.writeString(this.name);
        dest.writeString(this.origin_country);
    }

    public ModelProductionCompany() {
    }

    protected ModelProductionCompany(Parcel in) {
        this.id = in.readInt();
        this.logo_path = in.readString();
        this.name = in.readString();
        this.origin_country = in.readString();
    }

    public static final Parcelable.Creator<ModelProductionCompany> CREATOR = new Parcelable.Creator<ModelProductionCompany>() {
        @Override
        public ModelProductionCompany createFromParcel(Parcel source) {
            return new ModelProductionCompany(source);
        }

        @Override
        public ModelProductionCompany[] newArray(int size) {
            return new ModelProductionCompany[size];
        }
    };
}
