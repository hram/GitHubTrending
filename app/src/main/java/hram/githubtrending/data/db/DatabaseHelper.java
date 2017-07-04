package hram.githubtrending.data.db;

import android.support.annotation.NonNull;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;

/**
 * @author Evgeny Khramov
 */

public interface DatabaseHelper {

    Observable<List<Repository>> saveRepositories(@NonNull List<Repository> list, @NonNull String language, @NonNull String timeSpan);

    void saveRepository(@NonNull Repository repository);

    @NonNull
    Observable<List<Repository>> getRepositoriesObservable(@NonNull String language, @NonNull String timeSpan);

    @NonNull
    List<Repository> getRepositories(@NonNull String language, @NonNull String timeSpan);

    Observable<List<Language>> saveLanguages(@NonNull List<Language> list);

    @NonNull
    Observable<List<Language>> getLanguagesObservable();

    @NonNull
    List<Language> getLanguages();

    Observable<List<TimeSpan>> saveTimeSpans(@NonNull List<TimeSpan> list);

    @NonNull
    Observable<List<TimeSpan>> getTimeSpansObservable();

    @NonNull
    List<TimeSpan> getTimeSpans();

    Observable<Repository> getRepositoryObservable(@NonNull String id);

    Repository getRepositoryById(@NonNull String id);
}
