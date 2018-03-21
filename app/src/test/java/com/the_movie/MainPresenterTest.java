package com.the_movie;

import com.the_movie.comman.API;
import com.the_movie.model.ServerResponseModel;
import com.the_movie.ui.main_screen.MainContract;
import com.the_movie.ui.main_screen.MainPresenter;
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
public class MainPresenterTest {
    @Mock
    MainContract.View  mMainView;
    @Mock
    API mApi;

    private MainPresenter mMainPresenter;


    int mPage;
    boolean mVideo;
    int mYear;
    int mMovieId;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mPage = 1;
        mVideo = true;
        mYear = 2018;
        mMovieId = 2;

        mMainPresenter = new MainPresenter(mApi);
        mMainPresenter.attachView(mMainView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }



    @Test
    public void loadMovies() {

        ServerResponseModel serverResponseModel = TestDataFactory.makeSereverResponesModel();

        when(mApi.getMovies(mPage, mVideo, mYear))
                .thenReturn(Observable.just(serverResponseModel));

        mMainPresenter.loadMovies();
        verify(mMainView).loadMovies(serverResponseModel.getMovies());
        verify(mMainView, never()).showError();


        when(mApi.getMovies(mPage++, mVideo, mYear))
                .thenReturn(Observable.just(serverResponseModel));

        mMainPresenter.loadMovies();
        verify(mMainView).loadMore(serverResponseModel.getMovies());
        verify(mMainView, never()).showError();



    }


    @Test
    public void loadMoreMovies() {

        ServerResponseModel serverResponseModel = TestDataFactory.makeSereverResponesModel();

        when(mApi.getMovies(mPage++, mVideo, mYear))
                .thenReturn(Observable.just(serverResponseModel));

        mMainPresenter.loadMovies();
        verify(mMainView).loadMore(serverResponseModel.getMovies());
        verify(mMainView, never()).showError();



    }








}
