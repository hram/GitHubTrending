package hram.githubtrending.data.db;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.model.Repository;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author Evgeny Khramov
 */

public class DatabaseHelper {

    public Observable<List<Repository>> saveRepositories(@NonNull List<Repository> list, @NonNull String language, @NonNull String timeSpan) {
        final Realm realm = Realm.getDefaultInstance();
        List<Repository> saved = new ArrayList<>();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Repository.class)
                        .equalTo(Repository.COLUMN_LANGUAGE, language)
                        .equalTo(Repository.COLUMN_TIME_SPAN, timeSpan)
                        .equalTo(Repository.COLUMN_HIDED, false)
                        .findAll().deleteAllFromRealm();

                for (Repository item : list) {
                    if (isHided(item.getHref())) {
                        continue;
                    }

                    realm.copyToRealmOrUpdate(item);
                    saved.add(item);
                }
            }
        });

        return Observable.just(saved);
    }

    public void saveRepository(@NonNull Repository repository) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(repository);
            }
        });

    }

    @DebugLog
    @NonNull
    public Observable<List<Repository>> getRepositoriesObservable(@NonNull String language, @NonNull String timeSpan) {
        return Observable.defer(() -> Observable.just(getRepositories(language, timeSpan)));
    }

    @DebugLog
    @NonNull
    public List<Repository> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Repository> result = realm.where(Repository.class)
                .equalTo(Repository.COLUMN_LANGUAGE, language)
                .equalTo(Repository.COLUMN_TIME_SPAN, timeSpan)
                .equalTo(Repository.COLUMN_HIDED, false)
                .findAll()
                .sort(Repository.COLUMN_ORDER, Sort.DESCENDING);

        return realm.copyFromRealm(result);
    }

    public Observable<Repository> getRepositoryObservable(@NonNull String id) {
        return Observable.defer(() -> Observable.just(getRepositoryById(id)));
    }

    public Repository getRepositoryById(@NonNull String id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Repository> result = realm.where(Repository.class)
                .equalTo(Repository.COLUMN_ID, id)
                .findAll();
        if (result.isEmpty()) {
            return null;
        }
        return realm.copyFromRealm(result.first());
    }

    @DebugLog
    private boolean isHided(@NonNull String id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Repository> result = realm.where(Repository.class)
                .equalTo(Repository.COLUMN_ID, id)
                .findAll();
        return !result.isEmpty() && result.first().isHided();
    }
}
