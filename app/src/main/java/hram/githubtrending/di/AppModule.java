package hram.githubtrending.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hram.githubtrending.App;

/**
 * @author Evgeny Khramov
 */
@Module
public class AppModule {

    App mApplication;

    public AppModule(App application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    App providesApplication() {
        return mApplication;
    }
}
