package hram.githubtrending.data.db;

import android.support.annotation.NonNull;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;

/**
 * @author Dmitry Konurov
 */

public class RoomDatabaseHelper implements DatabaseHelper {

    @NonNull
    private final RepositoryDao mRepositoryDao;

    @NonNull
    private final LanguageDao mLanguageDao;

    @NonNull
    private final TimeSpanDao mTimeSpanDao;

    public RoomDatabaseHelper(@NonNull RepositoryDao repositoryDao,
                              @NonNull LanguageDao languageDao,
                              @NonNull TimeSpanDao timeSpanDao) {
        mRepositoryDao = repositoryDao;
        mLanguageDao = languageDao;
        mTimeSpanDao = timeSpanDao;
    }

    @Override
    public Observable<List<Repository>> saveRepositories(@NonNull List<Repository> list, @NonNull String language, @NonNull String timeSpan) {
        try {
            List<String> ids = mRepositoryDao.updateRepositories(list, language, timeSpan);
            return mRepositoryDao.observableByIds(ids).toObservable();
        } catch (Throwable ex) {
            return Observable.error(ex);
        }

    }

    @Override
    public void saveRepository(@NonNull Repository repository) {
        mRepositoryDao.save(repository);
    }

    @NonNull
    @Override
    public Observable<List<Repository>> getRepositoriesObservable(@NonNull String
                                                                          language, @NonNull String timeSpan) {
        return mRepositoryDao.observableByRelation(language, timeSpan).toObservable();
    }

    @Override
    public Observable<List<Language>> saveLanguages(@NonNull List<Language> list) {
        try {
            mLanguageDao.updateLanguage(list);
            return Observable.just(list);
        } catch (Throwable ex) {
            return Observable.error(ex);
        }
    }

    @NonNull
    @Override
    public Observable<List<Language>> getLanguagesObservable() {
        return mLanguageDao.observableAll().toObservable();
    }

    @Override
    public Observable<List<TimeSpan>> saveTimeSpans(@NonNull List<TimeSpan> list) {
        return mTimeSpanDao.updateTimeSpan(list).toObservable();
    }

    @NonNull
    @Override
    public Observable<List<TimeSpan>> getTimeSpansObservable() {
        return mTimeSpanDao.observableAll().toObservable();
    }

    @Override
    public Observable<Repository> getRepositoryObservable(@NonNull String id) {
        return mRepositoryDao.observableById(id).toObservable();
    }

    @Override
    public void clearAppData() {
        mRepositoryDao.clear();
        mLanguageDao.clear();
        mTimeSpanDao.clear();
    }
}
