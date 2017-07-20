package hram.githubtrending;

import android.support.annotation.NonNull;

import fr.xebia.android.freezer.Freezer;
import hram.githubtrending.di.AppComponent;
import hram.githubtrending.di.DaggerAppComponent;
import hram.githubtrending.di.NetworkModule;
import hram.githubtrending.di.TestDataModule;

/**
 * @author Evgeny Khramov
 */

public class TestApplication extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Freezer.onCreate(this);
    }

    @NonNull
    @Override
    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .dataModule(new TestDataModule())
                .build();
    }
}
