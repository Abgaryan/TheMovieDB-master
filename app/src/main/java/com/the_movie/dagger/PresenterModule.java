package com.the_movie.dagger;

import com.the_movie.comman.API;
import com.the_movie.ui.detail_screen.MovieDetailPresenter;
import com.the_movie.ui.main_screen.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



@Module
public class PresenterModule {
    @Provides
    @Singleton
    public MainPresenter MainPresenter(API api) {
        return new MainPresenter(api);
    }



    @Provides
    @Singleton
    public MovieDetailPresenter MovieDetailPresenter (API api) {
        return new MovieDetailPresenter (api);
    }
}
