package com.the_movie.ui.main_screen;

import com.the_movie.comman.API;
import com.the_movie.dagger.ConfigPersistent;
import com.the_movie.model.MovieModel;
import com.the_movie.model.ServerResponseModel;
import com.the_movie.rx.RxUtils;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Abgaryan on 3/21/18.
 * Listens to user actions from the UI ({@link MainActivity}), retrieves the data and updates the
 * UI as required.
 */
@ConfigPersistent
public class MainPresenter implements MainContract.Presenter, Provider<MainPresenter> {


    API mApi;

    private Disposable mDisposable;

    @NonNull
    private MainContract.View mMainView;

    private int mCurrentPage ;

    private int mFilterYear;



    @Inject
    public MainPresenter(API api) {
        mApi = api;
    }


    @Override
    public void attachView(@NonNull MainContract.View mainView) {
        mMainView = checkNotNull(mainView, "mainView cannot be null!");
    }


    @Override
    public void detachView() {
        if (mDisposable != null) mDisposable.dispose();

    }

    @Override
    public void loadMovies() {
        mMainView.showProgress();
        RxUtils.dispose(mDisposable);

        mCurrentPage = 1;

        loadMovies(mCurrentPage,true,mFilterYear);




    }

    @Override
    public void loadMore() {
        mCurrentPage++;
        mMainView.showProgressAdapter();
        mApi.getMovies(mCurrentPage,true,mFilterYear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ServerResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }


                    @Override
                    public void onNext(ServerResponseModel serverResponseModel) {
                        if (serverResponseModel.getMovies().size() > 0) {
                            ArrayList<MovieModel> movieModels =serverResponseModel.getMovies();
                            mMainView.loadMore(movieModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainView.hideProgressAdapter();
                        mMainView.showErrorAdapter();
                    }

                    @Override
                    public void onComplete() {
                        mMainView.hideProgressAdapter();
                    }
                });





    }




    private void loadMovies(int page,boolean video,int year){
        mApi.getMovies(page,video,year)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ServerResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }


                    @Override
                    public void onNext(ServerResponseModel serverResponseModel) {
                        mMainView.hideProgress();
                        if (serverResponseModel.getMovies().size() > 0) {
                            ArrayList<MovieModel> movieModels =serverResponseModel.getMovies();
                            mMainView.loadMovies(movieModels);
                        } else {
                            mMainView.showError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainView.hideProgress();
                        mMainView.showError();
                    }

                    @Override
                    public void onComplete() {
                        mMainView.hideProgress();
                    }
                });

    }












    @Override
    public void onItemClicked(int movieId) {
        mMainView.navigateToMovieDetail(movieId);
    }

    @Override
    public void menuFilterClicked() {
        mMainView.showFilterPicker();
    }

    @Override
    public void searchMoviesByReleaseDate(int year) {
        if(year != mFilterYear){
            mCurrentPage = 1;
            mFilterYear = year;
            mMainView.showProgress();
            RxUtils.dispose(mDisposable);
            mCurrentPage = 1;
            loadMovies(mCurrentPage,true,mFilterYear);

        }

    }

    @Override
    public MainPresenter get() {
        return this;
    }
}
