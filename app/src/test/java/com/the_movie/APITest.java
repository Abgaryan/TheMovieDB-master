package com.the_movie;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.the_movie.comman.API;
import com.the_movie.comman.Constants;
import com.the_movie.model.MovieDetailModel;
import com.the_movie.model.ServerResponseModel;

import org.junit.Before;
import org.junit.Test;

import common.TestDataFactory;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class APITest {


    API mApi;

    ServerResponseModel mServerResponseModel;
    MovieDetailModel mMovieDetailModel;

    MockWebServer mMockWebServer;
    TestObserver<ServerResponseModel> mSubscriber;
    TestObserver<MovieDetailModel> mSubscriberMovieDetails;

    int mPage;
    boolean mVideo;
    int mYear;
    int mMovieId;


    @Before
    public void setUp() {

        mPage = 1;
        mVideo = true;
        mYear = 2018;
        mMovieId = 2;

        mServerResponseModel = TestDataFactory.makeSereverResponesModel();
        mMovieDetailModel = TestDataFactory.makeModelMovieDetail();


        mMockWebServer = new MockWebServer();
        mSubscriber = new TestObserver<ServerResponseModel>();
        mSubscriberMovieDetails = new TestObserver<MovieDetailModel>();
    }


    @Test
    public void searchMovieCallWithError() {
        //Given
        String url = "dfdf/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mServerResponseModel)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();

        mApi = retrofit.create(API.class);
        //When
        mApi.getMovies(mPage, mVideo, mYear).subscribeWith(mSubscriber);

        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertComplete();
    }

    @Test
    public void searchMovieCallWithSuccessful() {
        //Given
        String url = Constants.BASE_URL;
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mServerResponseModel)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        mApi = retrofit.create(API.class);

        //When
        mApi.getMovies(mPage, mVideo, mYear).subscribeWith(mSubscriber);


        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertComplete();
    }


    @Test
    public void getMovieCallWithError() {
        //Given
        String url = "dfdf/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mMovieDetailModel)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();

        mApi = retrofit.create(API.class);
        //When
        mApi.getMovieDetails(mMovieId).subscribeWith(mSubscriberMovieDetails);

        //Then
        mSubscriberMovieDetails.assertNoErrors();
        mSubscriberMovieDetails.assertComplete();
    }

    @Test
    public void getMovieCallWithSuccessful() {
        //Given
        String url = Constants.BASE_URL;
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mMovieDetailModel)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        mApi = retrofit.create(API.class);
//When
        mApi.getMovieDetails(mMovieId).subscribeWith(mSubscriberMovieDetails);

        //Then
        mSubscriberMovieDetails.assertNoErrors();
        mSubscriberMovieDetails.assertComplete();
    }

}
