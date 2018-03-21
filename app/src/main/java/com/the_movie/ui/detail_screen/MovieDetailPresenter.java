package com.the_movie.ui.detail_screen;

import com.the_movie.comman.API;
import com.the_movie.model.MovieDetailModel;
import com.the_movie.rx.RxUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    API mApi;

    private Disposable mDisposable;

    @NonNull
    private MovieDetailContract.View mMovieDetailView;

    @Inject
    public MovieDetailPresenter(API mApi) {
        this.mApi = mApi;
    }

    @Override
    public void attachView(@NonNull MovieDetailContract.View detailView) {
        mMovieDetailView = checkNotNull(detailView, "mainView cannot be null!");
    }

    @Override
    public void detachView() {
        if (mDisposable != null) mDisposable.dispose();

    }


    /**
     * Gets Movie from webService by @link movie_id and loads MovieDatil model to DetailView
     *
     **/

    @Override
    public void loadMovie(int movie_id) {

        mMovieDetailView.showProgress();

        RxUtils.dispose(mDisposable);



        mApi.getMovieDetails(movie_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MovieDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }


                    @Override
                    public void onNext(MovieDetailModel movieDetailModel) {
                        mMovieDetailView.hideProgress();
                        if ( movieDetailModel.getStatus_message() == null) {

                            if(movieDetailModel.getOriginal_title() != null){
                                mMovieDetailView.setActivityTitle(movieDetailModel.getOriginal_title());
                            }

                            mMovieDetailView.loadMovie(movieDetailModel);

                            if(movieDetailModel.getProduction_companies().size()>0){

                                mMovieDetailView.loadCompanies(movieDetailModel.getProduction_companies());

                            }else {

                                mMovieDetailView.hideCompaniesView();
                            }




                        } else {
                            mMovieDetailView.showError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMovieDetailView.hideProgress();
                        mMovieDetailView.showError();
                    }

                    @Override
                    public void onComplete() {
                        mMovieDetailView.hideProgress();
                    }
                });





    }
}
