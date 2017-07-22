package hram.githubtrending.data.network;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.List;

import javax.inject.Inject;

import hram.githubtrending.App;
import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.LanguagesAndTimeSpans;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * @author Evgeny Khramov
 */
public class RetroJsoupNetworkHelper implements NetworkHelper {

    @Inject
    OkHttpClient mClient;

    @Inject
    HttpUrl mBaseUrl;

    public RetroJsoupNetworkHelper() {
        App.getInstance().getAppComponent().inject(this);
    }

    @NonNull
    public Observable<List<Repository>> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        final Trending trending = new RetroJsoup.Builder()
                .url(createUrl(language, timeSpan))
                .client(mClient)
                .build()
                .create(Trending.class);

        return trending.getRepositories()
                .flatMap(item -> setLanguageAndTimeSpan(item, language, timeSpan))
                .toList()
                .toObservable();
    }

    @NonNull
    public Observable<List<Language>> getLanguages() {
        final Trending trending = new RetroJsoup.Builder()
                .url(createUrl())
                .client(mClient)
                .build()
                .create(Trending.class);

        return trending.getLanguages()
                .toList().toObservable();
    }

    @NonNull
    public Observable<List<TimeSpan>> getTimeSpans() {
        final Trending trending = new RetroJsoup.Builder()
                .url(createUrl())
                .client(mClient)
                .build()
                .create(Trending.class);

        return trending.getTimeSpans()
                .toList().toObservable();
    }

    @NonNull
    public Observable<LanguagesAndTimeSpans> getLanguagesAndTimeSpans() {
        final Trending trending = new RetroJsoup.Builder()
                .url(createUrl())
                .client(mClient)
                .build()
                .create(Trending.class);

        return Observable.zip(trending.getLanguages().toList().toObservable(), trending.getTimeSpans().toList().toObservable(), LanguagesAndTimeSpans::new);
    }

    @NonNull
    private String createUrl(@NonNull String language, @NonNull String timeSpan) {
        return String.format("%s/trending/%s?since=%s", mBaseUrl, language, timeSpan);
    }

    @NonNull
    private String createUrl() {
        return String.format("%s/trending/", mBaseUrl);
    }

    @NonNull
    private Observable<Repository> setLanguageAndTimeSpan(@NonNull Repository item, @NonNull String language, @NonNull String timeSpan) {
        item.setLanguage(language);
        item.setTimeSpan(timeSpan);
        item.setOrder(Integer.parseInt(item.getStarsToday().split("\\s+")[0].replaceAll(",", "")));
        return Observable.just(item);
    }
}
