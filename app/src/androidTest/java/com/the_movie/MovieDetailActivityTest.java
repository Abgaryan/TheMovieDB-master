package com.the_movie;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.the_movie.model.ModelProductionCompany;
import com.the_movie.model.MovieDetailModel;
import com.the_movie.ui.main_screen.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

/**
 * Created by Abgaryan on 3/21/18.
 */
@RunWith(AndroidJUnit4.class)
public class MovieDetailActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> movieDetail =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext());
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(movieDetail);



    @Test
    public void loadCompanies() {
        MovieDetailModel movieDetailModel= TestDataFactory.makeModelMovieDetail();
        when(component.getMockAPI().getMovies(1))
                .thenReturn(io.reactivex.Observable.just(movieDetailModel));

        movieDetail.launchActivity(null);


            onView(ViewMatchers.withId(R.id.title_movie));
            String title = movieDetailModel.getOriginal_title();
            onView(withText(title));

        String tags = movieDetailModel.getTagline();
        onView(withText(tags));

        String overView = movieDetailModel.getOverview();
        onView(withText(overView);



    }

    @Test
    public void loadMoveDetail() {
        MovieDetailModel movieDetailModel= TestDataFactory.makeModelMovieDetail();
        when(component.getMockAPI().getMovies(1))
                .thenReturn(io.reactivex.Observable.just(movieDetailModel));

        movieDetail.launchActivity(null);

        int position = 0;

        for (ModelProductionCompany company : movieDetailModel.getProduction_companies()) {
            onView(ViewMatchers.withId(R.id.move_recyclerView))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            String name =company.getName();
            onView(withText(name)
                    .check(matches(isDisplayed())));
            String country =company.getOrigin_country();
            onView(withText(country))
                    .check(matches(isDisplayed()));
            position++;
        }

    }


}
