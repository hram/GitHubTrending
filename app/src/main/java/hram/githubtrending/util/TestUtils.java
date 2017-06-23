package hram.githubtrending.util;

import com.orhanobut.hawk.Hawk;

import io.realm.Realm;

import static hram.githubtrending.util.RealmUtils.close;
import static hram.githubtrending.util.RealmUtils.getRealm;

/**
 * @author Evgeny Khramov
 */

public class TestUtils {

    public static void clearAppData() {
        Hawk.clear();
        Realm realm = getRealm();
        realm.executeTransaction(realm1 -> realm.deleteAll());
        close(realm);
    }
}
