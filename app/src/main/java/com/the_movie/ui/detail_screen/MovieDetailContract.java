package com.the_movie.ui.detail_screen;

import com.the_movie.model.ModelProductionCompany;
import com.the_movie.model.MovieDetailModel;
import com.the_movie.ui.base.BasePresenter;
import com.the_movie.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by Abgaryan on 3/21/18.
 * This specifies the contract between the view and the presenter.
 */

public class MovieDetailContract {

    public interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void showError();

        void loadMovie(MovieDetailModel movieDetailModel);

        void loadCompanies(ArrayList<ModelProductionCompany> production_companies);

        void setActivityTitle(String title);

        void hideCompaniesView();



    }

    public  interface Presenter extends BasePresenter<View> {

        void loadMovie(int movie_id);


    }


}
