package com.the_movie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class MovieDetailModel implements Parcelable {

    String status_message;

    int budget;

    int revenue;

    ArrayList<ModelGenres> genres;

    String homepage;

    String original_title;

    String original_language;

    String overview;

    String poster_path;

    String backdrop_path;

    ArrayList<ModelProductionCompany> production_companies;


    String tagline;

    int vote_count;

    float vote_average;


    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public ArrayList<ModelGenres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<ModelGenres> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<ModelProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<ModelProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getAllGenres(){

        String tags = "";
        if(this.genres != null && this.genres.size()>0){
            for(ModelGenres modelGenre:genres){
                tags = tags + modelGenre.getName()+",";
            }
        }
        return tags;
    }


    public MovieDetailModel() {
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status_message);
        dest.writeInt(this.budget);
        dest.writeInt(this.revenue);
        dest.writeTypedList(this.genres);
        dest.writeString(this.homepage);
        dest.writeString(this.original_title);
        dest.writeString(this.original_language);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeList(this.production_companies);
        dest.writeString(this.tagline);
        dest.writeInt(this.vote_count);
        dest.writeFloat(this.vote_average);
    }

    protected MovieDetailModel(Parcel in) {
        this.status_message = in.readString();
        this.budget = in.readInt();
        this.revenue = in.readInt();
        this.genres = in.createTypedArrayList(ModelGenres.CREATOR);
        this.homepage = in.readString();
        this.original_title = in.readString();
        this.original_language = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.production_companies = new ArrayList<ModelProductionCompany>();
        in.readList(this.production_companies, ModelProductionCompany.class.getClassLoader());
        this.tagline = in.readString();
        this.vote_count = in.readInt();
        this.vote_average = in.readFloat();
    }

    public static final Creator<MovieDetailModel> CREATOR = new Creator<MovieDetailModel>() {
        @Override
        public MovieDetailModel createFromParcel(Parcel source) {
            return new MovieDetailModel(source);
        }

        @Override
        public MovieDetailModel[] newArray(int size) {
            return new MovieDetailModel[size];
        }
    };
}
