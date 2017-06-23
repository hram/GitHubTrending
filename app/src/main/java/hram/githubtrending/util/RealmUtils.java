package hram.githubtrending.util;

import android.support.annotation.NonNull;

import java.io.FileNotFoundException;

import hram.githubtrending.data.db.Migration;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Evgeny Khramov
 */

public class RealmUtils {

    private static final int SCHEMA_VERSION = 1;

    private static RealmConfiguration sConfiguration;

    private RealmUtils() {
    }

    public static Realm getRealm() {
        return Realm.getInstance(getConfiguration());
    }

    public static void close(@NonNull Realm realm) {
        realm.close();
    }

    private static RealmConfiguration getConfiguration() {
        if (sConfiguration == null) {
            sConfiguration = new RealmConfiguration.Builder()
                    .schemaVersion(SCHEMA_VERSION)
                    .build();

            try {
                Realm.migrateRealm(sConfiguration, new Migration());
            } catch (FileNotFoundException e) {
            }
        }
        return sConfiguration;
    }
}
