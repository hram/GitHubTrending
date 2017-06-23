package hram.githubtrending.data.db;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.util.RealmUtils;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author Evgeny Khramov
 */

public class DatabaseHelper {

    public Observable<List<Repository>> saveRepositories(@NonNull List<Repository> list, @NonNull String language, @NonNull String timeSpan) {
        final Realm realm = RealmUtils.getRealm();
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
        Realm realm = RealmUtils.getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(repository);
            }
        });

    }

    @NonNull
    public Observable<List<Repository>> getRepositoriesObservable(@NonNull String language, @NonNull String timeSpan) {
        return Observable.defer(() -> Observable.just(getRepositories(language, timeSpan)));
    }

    @NonNull
    public List<Repository> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        Realm realm = RealmUtils.getRealm();
        RealmResults<Repository> result = realm.where(Repository.class)
                .equalTo(Repository.COLUMN_LANGUAGE, language)
                .equalTo(Repository.COLUMN_TIME_SPAN, timeSpan)
                .equalTo(Repository.COLUMN_HIDED, false)
                .findAll()
                .sort(Repository.COLUMN_ORDER, Sort.DESCENDING);

        return realm.copyFromRealm(result);
    }

    public Observable<List<Language>> saveLanguages(@NonNull List<Language> list) {
        final Realm realm = RealmUtils.getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Language.class)
                        .findAll().deleteAllFromRealm();
                realm.copyToRealm(list);
            }
        });

        return Observable.just(list);
    }

    @NonNull
    public Observable<List<Language>> getLanguagesObservable() {
        return Observable.defer(() -> Observable.just(getLanguages()));
    }

    @NonNull
    public List<Language> getLanguages() {
        Realm realm = RealmUtils.getRealm();
        RealmResults<Language> result = realm.where(Language.class)
                .findAll();

        return realm.copyFromRealm(result);
    }

    public Observable<List<TimeSpan>> saveTimeSpans(@NonNull List<TimeSpan> list) {
        final Realm realm = RealmUtils.getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(TimeSpan.class)
                        .findAll().deleteAllFromRealm();
                realm.copyToRealm(list);
            }
        });

        return Observable.just(list);
    }

    @NonNull
    public Observable<List<TimeSpan>> getTimeSpansObservable() {
        return Observable.defer(() -> Observable.just(getTimeSpans()));
    }

    @NonNull
    public List<TimeSpan> getTimeSpans() {
        Realm realm = RealmUtils.getRealm();
        RealmResults<TimeSpan> result = realm.where(TimeSpan.class)
                .findAll();

        return realm.copyFromRealm(result);
    }

    public Observable<Repository> getRepositoryObservable(@NonNull String id) {
        return Observable.defer(() -> Observable.just(getRepositoryById(id)));
    }

    public Repository getRepositoryById(@NonNull String id) {
        Realm realm = RealmUtils.getRealm();
        RealmResults<Repository> result = realm.where(Repository.class)
                .equalTo(Repository.COLUMN_ID, id)
                .findAll();
        if (result.isEmpty()) {
            return null;
        }
        return realm.copyFromRealm(result.first());
    }

    private boolean isHided(@NonNull String id) {
        Realm realm = RealmUtils.getRealm();
        RealmResults<Repository> result = realm.where(Repository.class)
                .equalTo(Repository.COLUMN_ID, id)
                .findAll();
        return !result.isEmpty() && result.first().isHided();
    }
}
