package com.the_movie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class ServerResponseModel {
    private boolean success ;
    private int    status_code;
    private String status_message;


    int page;

    int total_results;

    int total_pages;


    @SerializedName("results")
    private ArrayList<MovieModel> movies;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieModel> movies) {
        this.movies = movies;
    }
}
