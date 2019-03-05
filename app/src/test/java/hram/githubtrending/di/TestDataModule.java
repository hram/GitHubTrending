package hram.githubtrending.di;

import android.support.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hram.githubtrending.SharedPreferencesHelper;
import hram.githubtrending.data.db.DatabaseHelper;
//import hram.githubtrending.data.db.RealtimeDatabaseHelper;
import hram.githubtrending.data.prefepences.PreferencesHelper;

/**
 * @author Evgeny Khramov
 */
@Module
public class TestDataModule extends DataModule {

    private final FirebaseDatabase mDatabase;

    public TestDataModule(@NonNull FirebaseDatabase database) {
        mDatabase = database;
    }

    @NonNull
    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return null; //new RealtimeDatabaseHelper(mDatabase);
    }

    @NonNull
    @Override
    PreferencesHelper providePreferencesHelper() {
        return new SharedPreferencesHelper();
    }
}
