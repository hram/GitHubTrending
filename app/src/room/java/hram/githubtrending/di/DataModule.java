package hram.githubtrending.di;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hram.githubtrending.BaseApp;
import hram.githubtrending.data.db.AppDatabase;
import hram.githubtrending.data.db.DatabaseHelper;
import hram.githubtrending.data.db.LanguageDao;
import hram.githubtrending.data.db.RepositoryDao;
import hram.githubtrending.data.db.RoomDatabaseHelper;
import hram.githubtrending.data.db.TimeSpanDao;
import hram.githubtrending.data.network.NetworkHelper;
import hram.githubtrending.data.network.RetroJsoupNetworkHelper;
import hram.githubtrending.data.prefepences.PreferencesHelper;
import hram.githubtrending.data.prefepences.SharedPreferencesHelper;

/**
 * @author Dmitry Konurov
 */

@Module
public class DataModule {

    @NonNull
    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(@NonNull RepositoryDao repositoryDao,
                                         @NonNull LanguageDao languageDao,
                                         @NonNull TimeSpanDao timeSpanDao) {
        return new RoomDatabaseHelper(repositoryDao, languageDao, timeSpanDao);
    }

    @NonNull
    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper() {
        return new SharedPreferencesHelper();
    }

    @NonNull
    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper() {
        return new RetroJsoupNetworkHelper();
    }

    @NotNull
    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(BaseApp.getInstance(),
                AppDatabase.class, AppDatabase.DATABASE_NAME).build();
    }

    @NotNull
    @Provides
    @Singleton
    RepositoryDao provideRepositoryDao(@NotNull AppDatabase appDatabase) {
        return appDatabase.repositoryDao();
    }

    @NotNull
    @Provides
    @Singleton
    LanguageDao provideLanguageDao(@NotNull AppDatabase appDatabase) {
        return appDatabase.languageDao();
    }

    @NotNull
    @Provides
    @Singleton
    TimeSpanDao provideTimeSpanDao(@NotNull AppDatabase appDatabase) {
        return appDatabase.timeSpanDao();
    }
}
