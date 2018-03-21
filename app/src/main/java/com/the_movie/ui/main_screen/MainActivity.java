package com.the_movie.ui.main_screen;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.the_movie.R;
import com.the_movie.TheMovieApplication;
import com.the_movie.comman.DialogHelper;
import com.the_movie.model.MovieModel;
import com.the_movie.ui.detail_screen.MovieDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.the_movie.comman.Constants.LIST_PAGE_LIMIT;
import static com.the_movie.comman.Constants.MOVIE_ID;

public class MainActivity extends AppCompatActivity implements MainContract.View,DatePickerDialog.OnDateSetListener {


    @BindView(R.id.move_recyclerView)
    public RecyclerView mMovieRecyclerView;


    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;


    @Inject
    MainPresenter mMainPresenter;

    @Inject
    MovieAdapter mMovieAdapter;


    private DialogHelper mDialogHelper = new DialogHelper();
    private boolean isRefreshing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((TheMovieApplication) getApplication()).getAppComponent().inject(this);

        initAdapters();
        initRecyclerView(mMovieRecyclerView);

        mMainPresenter.attachView(this);
        mMainPresenter.loadMovies();


    }


    /*
    * inits adapters for  mMovieRecyclerView ,mReviewedRecyclerView
    * */
    private void initAdapters() {
        mMovieRecyclerView.setAdapter(mMovieAdapter);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public void initRecyclerView(RecyclerView recyclerView) {

        RecyclerView.OnScrollListener osl = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisiblaPosition = lm.findLastVisibleItemPosition();
                int totalItemsInRV = recyclerView.getAdapter().getItemCount();
                if (lastVisiblaPosition >= totalItemsInRV - LIST_PAGE_LIMIT / 2) {
                    if (!isRefreshing) {
                        mMainPresenter.loadMore();
                    }
                }
            }
        };

        recyclerView.addOnScrollListener(osl);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                mMainPresenter.menuFilterClicked();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }


    /***** Main View methods implementation *****/

    @Override
    public void showProgress() {
        if (!mProgressBar.isShown()) mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        if (mProgressBar.isShown()) mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError() {
        mDialogHelper.showErrorPopup(MainActivity.this, getResources().getString(R.string.dialog_message_error));

    }

    @Override
    public void showProgressAdapter() {
        isRefreshing = true;
        mMovieAdapter.startLoading();

    }

    @Override
    public void hideProgressAdapter() {
        isRefreshing = false;
        mMovieAdapter.stopLoading();

    }

    @Override
    public void showErrorAdapter() {
        Snackbar.make(getCurrentFocus(), getResources().getString(R.string.load_more_message_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void loadMovies(List<MovieModel> movieModels) {
        mMovieRecyclerView.removeAllViewsInLayout();

        mMovieAdapter.setMovieModels(movieModels);
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<MovieModel> movieModels) {
        mMovieAdapter.addToList(movieModels);
        mMovieAdapter.notifyItemInserted(mMovieAdapter.getItemCount() - movieModels.size());
        mMovieAdapter.notifyDataSetChanged();

    }

    @Override
    public void navigateToMovieDetail(int movie_id) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID, movie_id);
        startActivity(intent);
    }

    @Override
    public void showFilterPicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this, (DatePickerDialog.OnDateSetListener) MainActivity.this,
                 2018, 31, 12);



        datePickerDialog.show();

    }


    public static Intent getStartIntent(Context targetContext) {
        return new Intent(targetContext, MainActivity.class);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mMainPresenter.searchMoviesByReleaseDate(year);
    }
}
