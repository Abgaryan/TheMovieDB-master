package com.the_movie.ui.base;

/**
 * Created by Abgaryan on 3/21/18.
 */

public interface BasePresenter<T> {

    void attachView(T view);


    void detachView();
}
