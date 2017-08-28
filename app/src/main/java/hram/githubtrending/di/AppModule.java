package hram.githubtrending.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hram.githubtrending.App;

/**
 * @author Evgeny Khramov
 */
@Module
public class AppModule {

    private App mApplication;

    public AppModule(@NonNull App application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    App providesApplication() {
        return mApplication;
    }
}
