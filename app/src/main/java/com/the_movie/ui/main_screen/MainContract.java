package com.the_movie.ui.main_screen;

import com.the_movie.model.MovieModel;
import com.the_movie.ui.base.BasePresenter;
import com.the_movie.ui.base.BaseView;

import java.util.List;

/**
 * Created by Abgaryan on 3/21/18.
 * This specifies the contract between the view and the presenter.
 */

public class MainContract {

    public interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void showError();

        void showProgressAdapter();

        void hideProgressAdapter();

        void  showErrorAdapter();

        void loadMovies(List<MovieModel> movieModels);

        void loadMore(List<MovieModel> movieModels);

        void navigateToMovieDetail(int movieId);

        void showFilterPicker();


    }

    public  interface Presenter extends BasePresenter<View> {

        void loadMovies();
        void loadMore();
        void onItemClicked(int movieId);
        void menuFilterClicked();
        void searchMoviesByReleaseDate(int year);

    }


}
