package hram.githubtrending;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import hram.githubtrending.di.AppComponent;
import hram.githubtrending.di.DaggerAppComponent;
import hram.githubtrending.di.DataModule;
import hram.githubtrending.di.NetworkModule;
import io.fabric.sdk.android.Fabric;

/**
 * @author Evgeny Khramov
 */

public class BaseApp extends Application {


    private static boolean sIsTestMode;

    private static BaseApp sInstance;

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics.Builder()
                    .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                    .build());
        }

        mAppComponent = buildComponent();

        try {
            Class.forName("ru.alfadirect.app.regress.smoke.AuthTest");
            sIsTestMode = true;
        } catch (final Exception e) {
            sIsTestMode = false;
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static void setInstance(@NonNull BaseApp instance) {
        sInstance = instance;
    }

    @NonNull
    public static BaseApp getInstance() {
        return sInstance;
    }

    @NonNull
    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .dataModule(new DataModule())
                .build();
    }

    @NonNull
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void setAppComponent(@NonNull AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}
