package com.the_movie.ui.detail_screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.the_movie.R;
import com.the_movie.model.ModelProductionCompany;
import com.the_movie.ui.main_screen.MainPresenter;

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

public class MovieCompanyAdapter extends RecyclerView.Adapter<MovieCompanyAdapter.MovieCompanyViewHolder> implements Provider<MovieCompanyAdapter> {


    List<ModelProductionCompany> companies;



    @Inject
    Context context;

    @Inject
    MainPresenter mMainPresenter;


    @Inject
    public MovieCompanyAdapter() {
        companies = new ArrayList<>();
    }

    @Override
    public MovieCompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new MovieCompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieCompanyViewHolder holder, int position) {

        ModelProductionCompany companyModel = companies.get(position);
        String str = companyModel.getName();
        holder.titleTextView.setText(str);

        String country = String.format(context.getResources().getString(R.string.country), companyModel.getOrigin_country());
        holder.countryTextView.setText(country );



        String poserPath = companyModel.getLogo_path();
        if (poserPath != null) {
            poserPath = POSTER_BASE_URL + poserPath;
            Picasso.get().load(poserPath).fit().centerCrop().into(holder.logoImageView);
        } else {
            Picasso.get().load("http://dummyimage.com/600x400/ffffff/000000.png&text=No+Image").fit().centerCrop().into(holder.logoImageView);
        }


    }



    public void setProductionsCompanies(List<ModelProductionCompany> productionCompanies) {
        this.companies = productionCompanies;
    }



    @Override
    public int getItemCount() {
        return companies.size();
    }

    @Override
    public MovieCompanyAdapter get() {
        return this;
    }


    class MovieCompanyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.company_title)
        TextView titleTextView;

        @BindView(R.id.country)
        TextView countryTextView;

        @BindView(R.id.logoImageView)
        ImageView logoImageView;



        public MovieCompanyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }




    }




}
