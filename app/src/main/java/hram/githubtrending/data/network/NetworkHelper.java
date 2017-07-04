package hram.githubtrending.data.network;

import android.support.annotation.NonNull;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;

/**
 * @author Evgeny Khramov
 */
public interface NetworkHelper {

    @NonNull
    Observable<List<Repository>> getRepositories(@NonNull String language, @NonNull String timeSpan);

    @NonNull
    Observable<List<Language>> getLanguages();

    @NonNull
    Observable<List<TimeSpan>> getTimeSpans();
}
