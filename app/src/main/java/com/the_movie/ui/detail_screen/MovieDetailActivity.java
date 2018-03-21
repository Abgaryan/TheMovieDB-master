package com.the_movie.ui.detail_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.the_movie.R;
import com.the_movie.TheMovieApplication;
import com.the_movie.model.ModelProductionCompany;
import com.the_movie.model.MovieDetailModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.the_movie.comman.Constants.MOVIE_ID;
import static com.the_movie.comman.Constants.POSTER_BASE_URL;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View{

    @Inject
    MovieDetailPresenter mMovieDetailPresenter;

    @Inject
    MovieCompanyAdapter mMovieCompanyAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;

    @BindView(R.id.detail_parent_view)
    CoordinatorLayout mParentView;

    @BindView(R.id.poster_movie_image_view)
    ImageView mPosterImageView;

    @BindView(R.id.title_move)
    TextView mTitleTextView;


    @BindView(R.id.movie_tags)
    TextView mTagsTextView;

    @BindView(R.id.movie_genre)
    TextView mGenreTextView;

    @BindView(R.id.movie_budget)
    TextView mBudgetTextView;

    @BindView(R.id.movie_rating)
    TextView mRatingTextView;

    @BindView(R.id.movie_overview)
    TextView mOverviewTextView;


    @BindView(R.id.company_recyclerView)
    RecyclerView mCompanyRecyclerView;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ((TheMovieApplication) getApplication()).getAppComponent() .inject(this);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mMovieDetailPresenter.attachView(this);
        Intent intent = null;
        intent = getIntent();

        if(intent!= null){
            mMovieDetailPresenter.loadMovie(intent.getIntExtra(MOVIE_ID,0));
        }else {
            showError();
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMovieDetailPresenter.detachView();
    }






    /**
     * MovieDetail View methods implementation
     **/

    @Override
    public void showProgress() {
        if( !mProgressBar.isShown()) mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        if( mProgressBar.isShown()) mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar.make(mParentView, getResources().getString(R.string.load_more_message_error), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void loadMovie(MovieDetailModel movieDetailModel) {

        String poserPath =movieDetailModel.getBackdrop_path();
        if (poserPath != null) {
            poserPath = POSTER_BASE_URL + poserPath;
            Picasso.get().load(poserPath).fit().centerCrop().into(mPosterImageView);
        } else {
            Picasso.get().load("http://dummyimage.com/600x400/ffffff/000000.png&text=No+Image").fit().centerCrop().into(mPosterImageView);
        }


        mTitleTextView.setText(movieDetailModel.getOriginal_title());

        mTagsTextView.setText(movieDetailModel.getTagline());

        String geners = String.format(getResources().getString(R.string.tags), movieDetailModel.getAllGenres());

        mGenreTextView.setText(geners);

        String rating  = String.format(getResources().getString(R.string.rating), movieDetailModel.getVote_average(),movieDetailModel.getVote_count());

        String budget  = String.format(getResources().getString(R.string.budget), movieDetailModel.getBudget(),movieDetailModel.getRevenue());

        mBudgetTextView.setText(budget);

        mRatingTextView.setText(rating);



        mOverviewTextView.setText(movieDetailModel.getOverview());




    }


    @Override
    public void loadCompanies(ArrayList<ModelProductionCompany> production_companies) {
        mMovieCompanyAdapter.setProductionsCompanies(production_companies);
        mCompanyRecyclerView.setAdapter(mMovieCompanyAdapter);
        mCompanyRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


    }

    @Override
    public void setActivityTitle(String title) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);

    }

    @Override
    public void hideCompaniesView() {
        mCompanyRecyclerView.setVisibility(View.GONE);

    }

    public static Intent getStartIntent(Context targetContext) {
        return new Intent(targetContext, MovieDetailActivity.class);
    }
}
