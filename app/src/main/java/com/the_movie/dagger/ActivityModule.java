package com.the_movie.dagger;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abgaryan on 3/21/18.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    Activity getActivity() {
        return this.mActivity;
    }

}
