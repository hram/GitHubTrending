package hram.githubtrending.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hram.githubtrending.data.db.DatabaseHelper;
import hram.githubtrending.data.db.RealmDatabaseHelper;
import hram.githubtrending.data.network.NetworkHelper;
import hram.githubtrending.data.network.RetroJsoupNetworkHelper;
import hram.githubtrending.data.prefepences.HawkPreferencesHelper;
import hram.githubtrending.data.prefepences.PreferencesHelper;

/**
 * @author Evgeny Khramov
 */
@Module
public class DataModule {

    @NonNull
    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return new RealmDatabaseHelper();
    }

    @NonNull
    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper() {
        return new HawkPreferencesHelper();
    }

    @NonNull
    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper() {
        return new RetroJsoupNetworkHelper();
    }
}
