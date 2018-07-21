package hram.githubtrending.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import hram.githubtrending.data.db.AppDatabase;
import hram.githubtrending.data.db.LanguageDao;
import hram.githubtrending.data.db.RepositoryDao;
import hram.githubtrending.data.db.TimeSpanDao;

/**
 * @author Dmitry Konurov
 */

public class TestDataModule {

    @NotNull
    private final AppDatabase mAppDatabase;

    public TestDataModule(@NotNull Context context) {
        mAppDatabase = provideAppDatabase(context);
    }


    private AppDatabase provideAppDatabase(Context context) {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries().build();
    }

    @NotNull
    public RepositoryDao provideRepositoryDao() {
        return mAppDatabase.repositoryDao();
    }


    @NotNull
    public LanguageDao provideLanguageDao() {
        return mAppDatabase.languageDao();
    }

    @NotNull
    public TimeSpanDao provideTimeSpanDao() {
        return mAppDatabase.timeSpanDao();
    }

    public void close() {
        mAppDatabase.clearAllTables();
    }
}
