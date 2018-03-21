package com.the_movie.dagger;

import android.content.Context;

import com.the_movie.comman.API;
import com.the_movie.ui.detail_screen.MovieDetailActivity;
import com.the_movie.ui.detail_screen.MovieDetailPresenter;
import com.the_movie.ui.main_screen.MainActivity;
import com.the_movie.ui.main_screen.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abgaryan on 3/21/18.
 */
@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                NetModule.class,
                PresenterModule.class
        }
)
public interface ApplicationComponent {
    Context context();
    API api();



    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(MovieDetailActivity movieDetailActivity);

    void inject(MovieDetailPresenter movieDetailPresenter);


}
