package hram.githubtrending.di;

import android.support.annotation.NonNull;

import dagger.Module;
import hram.githubtrending.SharedPreferencesHelper;
import hram.githubtrending.data.prefepences.PreferencesHelper;

/**
 * @author Evgeny Khramov
 */
@Module
public class TestDataModule extends DataModule {

    @NonNull
    @Override
    PreferencesHelper providePreferencesHelper() {
        return new SharedPreferencesHelper();
    }
}
