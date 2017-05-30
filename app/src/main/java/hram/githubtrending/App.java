package hram.githubtrending;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.orhanobut.hawk.Hawk;

import io.realm.Realm;

/**
 * @author Evgeny Khramov
 */

public class App extends Application {

    private static boolean sIsTestMode;

    @Override
    public void onCreate() {
        super.onCreate();

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
}
