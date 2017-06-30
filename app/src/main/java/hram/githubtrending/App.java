package hram.githubtrending;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.hawk.Hawk;

import hram.githubtrending.di.AppComponent;
import hram.githubtrending.di.DaggerAppComponent;
import hram.githubtrending.di.NetworkModule;
import io.realm.Realm;

/**
 * @author Evgeny Khramov
 */

public class App extends Application {

    private static boolean sIsTestMode;

    private static App sInstance;

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        mAppComponent = buildComponent();

        try {
            Class.forName("ru.alfadirect.app.regress.smoke.AuthTest");
            sIsTestMode = true;
        } catch (final Exception e) {
            sIsTestMode = false;
        }

        if (!BuildConfig.DEBUG) {
            //Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        }

        Realm.init(this);
        Hawk.init(this).build();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static void setInstance(@NonNull App instance) {
        sInstance = instance;
    }

    @NonNull
    public static App getInstance() {
        return sInstance;
    }

    @NonNull
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
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
