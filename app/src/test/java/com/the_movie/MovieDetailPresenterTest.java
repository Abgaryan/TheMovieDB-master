package com.the_movie;

import com.the_movie.comman.API;
import com.the_movie.model.MovieDetailModel;
import com.the_movie.ui.detail_screen.MovieDetailContract;
import com.the_movie.ui.detail_screen.MovieDetailPresenter;
import com.the_movie.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import common.TestDataFactory;
import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Abgaryan on 3/21/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailPresenterTest {
    @Mock
    MovieDetailContract.View mMovieDetailView;

    @Mock
    API mApi;

    private MovieDetailPresenter mDetailMoviePresenter;

    int mMovieId;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {

        mMovieId = 2;

        mDetailMoviePresenter = new MovieDetailPresenter(mApi);
        mDetailMoviePresenter.attachView(mMovieDetailView);
    }

    @After
    public void tearDown() {
        mDetailMoviePresenter.detachView();
    }



    @Test
    public void loadMoveDetail() {

        MovieDetailModel movieDetailModel= TestDataFactory.makeModelMovieDetail();

        when(mApi.getMovieDetails(mMovieId))
                .thenReturn(Observable.just(movieDetailModel));

        mDetailMoviePresenter.loadMovie(mMovieId);

        verify(mMovieDetailView).loadMovie(movieDetailModel);
        verify(mMovieDetailView, never()).showError();



    }



    @Test
    public void setActivtyTitle() {

        MovieDetailModel movieDetailModel= TestDataFactory.makeModelMovieDetail();

        when(mApi.getMovieDetails(mMovieId))
                .thenReturn(Observable.just(movieDetailModel));

        mDetailMoviePresenter.loadMovie(mMovieId);
        verify(mMovieDetailView).setActivityTitle(movieDetailModel.getOriginal_title());

        verify(mMovieDetailView, never()).showError();




    }


    @Test
    public void loadCompanies() {

        MovieDetailModel movieDetailModel= TestDataFactory.makeModelMovieDetail();

        when(mApi.getMovieDetails(mMovieId))
                .thenReturn(Observable.just(movieDetailModel));

        mDetailMoviePresenter.loadMovie(mMovieId);

        verify(mMovieDetailView).loadCompanies(movieDetailModel.getProduction_companies());
        verify(mMovieDetailView, never()).showError();




    }





}
