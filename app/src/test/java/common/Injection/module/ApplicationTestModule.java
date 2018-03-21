package common.Injection.module;

import android.content.Context;

import com.the_movie.TheMovieApplication;
import com.the_movie.comman.API;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by Abgaryan on 3/21/18.
 ** Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private Context mApplication;
    public ApplicationTestModule(TheMovieApplication application) {
        mApplication = application;
    }

//    @Provides
//    @Singleton
//    TheMovieApplication providesApplication() {
//        return mApplication;
//    }


    @Provides
    @Singleton
    public Context getContext() {
        return mApplication;
    }

    /************* MOCk *************/

    @Provides
    @Singleton
    API provideAPI() {
        return mock(API.class);
    }



}