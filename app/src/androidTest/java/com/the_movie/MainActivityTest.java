package com.the_movie;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.the_movie.model.MovieModel;
import com.the_movie.model.ServerResponseModel;
import com.the_movie.ui.detail_screen.MovieDetailActivity;
import com.the_movie.ui.main_screen.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Abgaryan on 3/21/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MovieDetailActivity> main =
            new ActivityTestRule<MovieDetailActivity>(MovieDetailActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MovieDetailActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext());
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);



    @Test
    public void loadMovies() {
        ServerResponseModel serverResponseModel = TestDataFactory.makeSereverResponesModel();

        when(component.getMockAPI().getMovieDetails(1))
                .thenReturn(io.reactivex.Observable.just(serverResponseModel));

        main.launchActivity(null);


        for (MovieModel movieModel : serverResponseModel.getMovies()) {
            onView(ViewMatchers.withId(R.id.move_recyclerView))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            String str =movieModel.getOriginal_title();
            onView(withText(str))
                    .check(matches(isDisplayed()));
            String date =movieModel.getRelease_date();
            onView(withText(date))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

}
