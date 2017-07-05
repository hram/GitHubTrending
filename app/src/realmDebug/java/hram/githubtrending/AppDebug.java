package hram.githubtrending;


import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * @author Evgeny Khramov
 */
public class AppDebug extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.enableDefaults();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}