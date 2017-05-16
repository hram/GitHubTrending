package hram.githubtrending.data.db;

import android.support.annotation.NonNull;

import java.util.List;

import hram.githubtrending.data.model.Repository;
import io.reactivex.Observable;
import io.realm.Realm;

/**
 * @author Evgeny Khramov
 */

public class DatabaseHelper {

    public void saveRepositories(List<Repository> list) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Repository item : list) {
                    realm.copyToRealmOrUpdate(item);
                }
            }
        });

    }

    public void saveRepository(Repository repository) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(repository);
            }
        });

    }

    @NonNull
    public Observable<List<Repository>> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        return Observable.empty();
    }
}
