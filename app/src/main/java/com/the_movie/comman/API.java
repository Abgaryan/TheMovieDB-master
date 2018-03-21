package com.the_movie.comman;

import com.the_movie.model.MovieDetailModel;
import com.the_movie.model.ServerResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Abgaryan on 3/21/18.
 */
public interface API {

    @GET("discover/movie?api_key=98a2699b40051b435f84479a66cdc45b")
    Observable<ServerResponseModel> getMovies(@Query("page") int pg,
                                              @Query("include_video") boolean includeVideo,
                                              @Query("year")int year );

    @GET("movie/{movie_id}?api_key=98a2699b40051b435f84479a66cdc45b")
    Observable<MovieDetailModel> getMovieDetails(@Path("movie_id") int id);

}

