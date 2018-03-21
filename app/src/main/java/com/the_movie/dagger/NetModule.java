package com.the_movie.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.the_movie.comman.API;
import com.the_movie.comman.Constants;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abgaryan on 3/21/18.
 */

@Module
public class NetModule {



    @Provides
    @Singleton
    @Named("header")
    public Interceptor getConnectionInterceptor(Context context){
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().build());

            }
        };
    }

    @Provides
    @Singleton
    public OkHttpClient getOkHttpClient(@Named("logger") Interceptor loggingInterceptor,
                                        @Named("header") Interceptor headerInterceptor,
                                        @Named("connection") Interceptor connectionInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(connectionInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public Gson getGson(){
        return new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
    }

    @Provides
    @Singleton
    @Named("logger")
    public Interceptor getLoggerInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    @Named("connection")
    public Interceptor getHeaderInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                return chain.proceed(chain.request());
            }
        };
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    @Provides
    @Singleton
    public API getApi(Retrofit retrofit){
        return retrofit.create(API.class);
    }



}