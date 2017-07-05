package hram.githubtrending.data.db;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * @author Evgeny Khramov
 */

public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // do nothing
    }
}
