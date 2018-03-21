package com.the_movie.dagger;

import android.content.Context;

import com.the_movie.TheMovieApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abgaryan on 3/21/18.
 *
 * This is where you will inject application-wide dependencies.
 */
@Module
public class ApplicationModule {

   private Context mApplication;

    public ApplicationModule(TheMovieApplication application) {
        mApplication = application;
    }

//    @Provides
//    @Singleton
//    TheMovieApplication providesApplication() {
//        return mApplication;
//    }

    @Provides
    @Singleton
    public Context getContext() {
        return mApplication;
    }

}
