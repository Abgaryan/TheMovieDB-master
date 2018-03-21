package com.the_movie.ui.main_screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.the_movie.R;
import com.the_movie.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.the_movie.comman.Constants.POSTER_BASE_URL;

/**
 * Created by Abgaryan on 3/21/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Provider<MovieAdapter> {
    private static final int USUAL_ADAPTER = 1;
    private static final int FOOTER_ADAPTER = 2;


    private boolean isLoading = false;

    List<MovieModel> mMovieModels;
    private RecyclerView.ViewHolder holder;
    private int position;

    @Inject
    public MovieAdapter() {
        mMovieModels = new ArrayList<>();
    }

    @Inject
    Context context;

    @Inject
    MainPresenter mMainPresenter;


    public void setMovieModels(List<MovieModel> movieModels) {
        this.mMovieModels = movieModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;

        switch (viewType) {
            case USUAL_ADAPTER :
                view = inflateUsualView(parent);
                break;
            case FOOTER_ADAPTER:
                view = inflateFooterView(parent);
                return new LoadingViewHolder(view);
            default:
                view = null;
        }

        return new MovieViewHolder(view);


    }


    @Override
    public int getItemViewType(int position) {
        if (mMovieModels.get(position) == null) {
            return FOOTER_ADAPTER;
        }
        return USUAL_ADAPTER;
    }

    private View inflateFooterView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer_loading_item, parent, false);

    }

    private View inflateUsualView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
    }


    public void startLoading() {
        if (!isLoading && mMovieModels.size() > 2) {
            mMovieModels.add(null);
            notifyItemInserted(mMovieModels.size() - 1);
            isLoading = true;
        }

    }

    public void stopLoading() {
        if (isLoading) {
            mMovieModels.remove(mMovieModels.size() - 1);
            notifyItemRemoved(mMovieModels.size());
            isLoading = false;
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

            MovieModel movieModel = mMovieModels.get(position);
            String str = movieModel.getOriginal_title();
            movieViewHolder.titleMovieTextView.setText(str);

            String reales = String.format(context.getResources().getString(R.string.release_date), movieModel.getRelease_date());
            movieViewHolder.releaseDateTextView.setText(reales);

//        holder.descriptionTextView.setText(movieModel.getOverview());

            String poserPath = movieModel.getBackdrop_path();
            if (poserPath != null) {
                poserPath = POSTER_BASE_URL + poserPath;
                Picasso.get().load(poserPath).fit().centerCrop().into(movieViewHolder.posterImageView);
            } else {
                Picasso.get().load("http://dummyimage.com/600x400/ffffff/000000.png&text=No+Image").fit().centerCrop().into(movieViewHolder.posterImageView);
            }
            float rating = movieModel.getVote_average() / 2;
            movieViewHolder.ratingBar.setRating(rating);
            movieViewHolder.setmMovieModel(movieModel);
            holder.itemView.setTag(movieViewHolder);

        } else {
            LoadingViewHolder LoadingHolder = (LoadingViewHolder) holder;
            LoadingHolder.progressBar.setIndeterminate(true);
        }


    }

    @Override
    public int getItemCount() {
        return mMovieModels.size();
    }

    @Override
    public MovieAdapter get() {
        return this;
    }

    public void addToList(List<MovieModel> movieModels) {
        mMovieModels.addAll(movieModels);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.title_movie)
        TextView titleMovieTextView;

        @BindView(R.id.relase_date_movie)
        TextView releaseDateTextView;

        @BindView(R.id.poster_movie_image_view)
        ImageView posterImageView;

//        @BindView(R.id.description)
//        TextView descriptionTextView;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        public MovieModel mMovieModel;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setInItemViewClick(itemView);

        }

        public void setmMovieModel(MovieModel mMovieModel) {
            this.mMovieModel = mMovieModel;
        }

        private void setInItemViewClick(View itemView) {
            itemView.setOnClickListener((v) -> {
                mMainPresenter.onItemClicked(this.mMovieModel.getId());

            });
        }

    }


    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBarFooter)
        ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
