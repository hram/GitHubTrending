package hram.githubtrending.data.db;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.LanguageEntityManager;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.RepositoryEntityManager;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.data.model.TimeSpanEntityManager;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;

/**
 * @author Evgeny Khramov
 */
public class FreezerDatabaseHelper implements DatabaseHelper {

    private final RepositoryEntityManager mRepositories = new RepositoryEntityManager();

    private final LanguageEntityManager mLanguages = new LanguageEntityManager();

    private final TimeSpanEntityManager mTimeSpans = new TimeSpanEntityManager();

    @DebugLog
    @Override
    public Observable<List<Repository>> saveRepositories(@NonNull List<Repository> list, @NonNull String language, @NonNull String timeSpan) {
        mRepositories.delete(mRepositories.select()
                .mLanguage().equalsTo(language)
                .and()
                .mTimeSpan().equalsTo(timeSpan)
                .and()
                .mIsHided().isFalse()
                .asList());

        final List<Repository> saved = new ArrayList<>();
        for (Repository item : list) {
            if (isHided(item.getHref())) {
                continue;
            }

            final Repository repository = mRepositories.select()
                    .mHref().equalsTo(item.getHref())
                    .and()
                    .mLanguage().equalsTo(language)
                    .and()
                    .mTimeSpan().equalsTo(timeSpan)
                    .first();

            if (repository != null) {
                repository.setUser(item.getUser());
                repository.setTitle(item.getTitle());
                repository.setHided(item.isHided());
                repository.setOrder(item.getOrder());
                repository.setDescription(item.getDescription());
                repository.setAllStars(item.getAllStars());
                repository.setForks(item.getForks());
                repository.setStarsToday(item.getStarsToday());
                mRepositories.update(repository);
            } else {
                mRepositories.add(item);
            }
            saved.add(item);
        }

        return Observable.just(saved);
    }

    @Override
    public void saveRepository(@NonNull Repository repository) {
        mRepositories.update(repository);
    }

    @NonNull
    @Override
    public Observable<List<Repository>> getRepositoriesObservable(@NonNull String language, @NonNull String timeSpan) {
        return Observable.defer(() -> Observable.just(getRepositories(language, timeSpan)));
//        return mRepositories.select()
//                .mLanguage().equalsTo(language)
//                .and()
//                .mTimeSpan().equalsTo(timeSpan)
//                .asObservable();
    }

    @NonNull
    @Override
    public List<Repository> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        return mRepositories.select()
                .mLanguage().equalsTo(language)
                .and()
                .mTimeSpan().equalsTo(timeSpan)
                .asList();
    }

    @Override
    public Observable<List<Language>> saveLanguages(@NonNull List<Language> list) {
        return Observable.defer(() -> Observable.just(saveAndGetLanguages(list)));
    }

    @DebugLog
    @NonNull
    private List<Language> saveAndGetLanguages(@NonNull List<Language> list) {
        mLanguages.deleteAll();
        mLanguages.add(list);
        return mLanguages.select().asList();
    }

    @DebugLog
    @NonNull
    @Override
    public Observable<List<Language>> getLanguagesObservable() {
        return Observable.defer(() -> Observable.just(getLanguages()));
    }

    @NonNull
    @Override
    public List<Language> getLanguages() {
        return mLanguages.select().asList();
    }

    @Override
    public Observable<List<TimeSpan>> saveTimeSpans(@NonNull List<TimeSpan> list) {
        return Observable.defer(() -> Observable.just(saveAndGetTimeSpans(list)));
    }

    @NonNull
    private List<TimeSpan> saveAndGetTimeSpans(@NonNull List<TimeSpan> list) {
        mTimeSpans.deleteAll();
        mTimeSpans.add(list);
        return mTimeSpans.select().asList();
    }

    @NonNull
    @Override
    public Observable<List<TimeSpan>> getTimeSpansObservable() {
        return Observable.defer(() -> Observable.just(getTimeSpans()));
    }

    @NonNull
    @Override
    public List<TimeSpan> getTimeSpans() {
        return mTimeSpans.select().asList();
    }

    @Override
    public Observable<Repository> getRepositoryObservable(@NonNull String id) {
        return Observable.just(getRepositoryById(id));
    }

    @Override
    public Repository getRepositoryById(@NonNull String id) {
        return mRepositories.select()
                .mHref().equalsTo(id)
                .first();
    }

    @Override
    public void clearAppData() {
        mRepositories.deleteAll();
        mLanguages.deleteAll();
        mTimeSpans.deleteAll();
    }

    private boolean isHided(@NonNull String id) {
        final List<Repository> res = mRepositories.select().mHref().equalsTo(id).asList();
        return !res.isEmpty() && res.get(0).isHided();
    }
}
