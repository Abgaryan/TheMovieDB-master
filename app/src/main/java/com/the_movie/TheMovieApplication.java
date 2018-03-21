package com.the_movie;

import android.app.Application;
import android.content.Context;

import com.the_movie.dagger.ApplicationComponent;
import com.the_movie.dagger.ApplicationModule;
import com.the_movie.dagger.DaggerApplicationComponent;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class TheMovieApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();

    }




    private void initAppComponent(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent(){
        if (applicationComponent == null) {
            initAppComponent();
        }

        return applicationComponent;
    }



    public static TheMovieApplication get(Context context) {
        return (TheMovieApplication) context.getApplicationContext();
    }



    public void setComponent(ApplicationComponent applicationComponent) {
        applicationComponent = applicationComponent;
    }
}
