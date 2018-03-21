package common;

import com.the_movie.model.ModelGenres;
import com.the_movie.model.ModelProductionCompany;
import com.the_movie.model.MovieDetailModel;
import com.the_movie.model.MovieModel;
import com.the_movie.model.ServerResponseModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Abgaryan on 3/21/18.
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */

public class TestDataFactory {
    public static String randomString() {
        return UUID.randomUUID().toString();
    }


    public static ServerResponseModel makeSereverResponesModel() {
        ServerResponseModel serverResponseModel = new ServerResponseModel();
        serverResponseModel.setPage(1);
        serverResponseModel.setMovies(makeListMovies(10));


        return serverResponseModel;
    }

    public static ArrayList<MovieModel> makeListMovies(int size) {
        ArrayList<MovieModel> movieList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            movieList.add(makeModelMovie(i));
        }
        return movieList;
    }


    public static MovieModel makeModelMovie(int id) {
        MovieModel movieModel = new MovieModel();
        movieModel.setId(id);
        movieModel.setBackdrop_path("/6KXbhaxkgExC5EdDqAzRinhmoZ8.jpg");
        movieModel.setOriginal_title("Twelve Monkeys");
        movieModel.setVote_count(23424);
        movieModel.setVote_average(7);
        movieModel.setRelease_date("1995-12-29");
        movieModel.setTitle("Twelve Monkeys");
        movieModel.setOverview("Wreck-It Ralph is the 9-foot-tall, 643-pound villain of an arcade video game named Fix-It Felix Jr., in which the game's titular hero fixes buildings that Ralph destroys. Wanting to prove he can be a good guy and not just a villain, Ralph escapes his game and lands in Hero's Duty, a first-person shooter where he helps the game's hero battle against alien invaders. He later enters Sugar Rush, a kart racing game set on tracks made of candies, cookies and other sweets. There, Ralph meets Vanellope von Schweetz who has learned that her game is faced with a dire threat that could affec" +
                "t the entire arcade, and one that Ralph may have inadvertently started.");
        movieModel.setPoster_path("/93FsllrXXWncp7BQYTdOU1XMRXo.jpg");

        return movieModel;
    }

    public static MovieDetailModel makeModelMovieDetail() {

        MovieDetailModel detailModel = new MovieDetailModel();
        detailModel.setBudget(100130102);

        detailModel.setBackdrop_path("/6KXbhaxkgExC5EdDqAzRinhmoZ8.jpg");
        detailModel.setOverview("Wreck-It Ralph is the 9-foot-tall, 643-pound villain of an arcade video game named Fix-It Felix Jr., in which the game's titular hero fixes buildings that Ralph destroys. Wanting to prove he can be a good guy and not just a villain, Ralph escapes his game and lands in Hero's Duty, a first-person shooter where he helps the game's hero battle against alien invaders. He later enters Sugar Rush, a kart racing game set on tracks made of candies, cookies and other sweets. There, Ralph meets Vanellope von Schweetz who has learned that her game is faced with a dire threat that could affec" +
                "t the entire arcade, and one that Ralph may have inadvertently started.");
        detailModel.setGenres(makeModelGenres(2));
        detailModel.setOriginal_title("Twelve Monkeys");
        detailModel.setVote_count(23424);
        detailModel.setVote_average(7);
        detailModel.setRevenue(2112323424);
        detailModel.setVote_count(23424);
        detailModel.setProduction_companies(makeModelProductionCompanies(2));


        return detailModel;


    }

    public static ArrayList<ModelGenres> makeModelGenres(int size) {

        ArrayList<ModelGenres> genreList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            genreList.add(makeModelGenre());
        }
        return genreList;


    }



    public static ModelGenres makeModelGenre() {

        ModelGenres modelGenres = new ModelGenres();
        modelGenres.setId(1);
        modelGenres.setName("Romance");
        return modelGenres;
    }


    public static ArrayList<ModelProductionCompany> makeModelProductionCompanies(int size) {

        ArrayList<ModelProductionCompany> companyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            companyList.add(makeModelProductionCompany());
        }


        return  companyList;


    }

    public static ModelProductionCompany makeModelProductionCompany() {

       ModelProductionCompany company = new ModelProductionCompany();
       company.setId(123);
       company.setName("Walt Disney");
       company.setOrigin_country("US");
       company.setLogo_path("/gZCJZOn4l0Zj5hAxsMbxoS6CL0u.jpg");
       return company;


    }




}
