package hram.githubtrending.data.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.List;

import javax.inject.Inject;

import hram.githubtrending.App;
import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.di.AppComponent;
import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * @author Evgeny Khramov
 */
public class NetworkHelper {

    @Inject
    OkHttpClient mClient;

    @Inject
    HttpUrl mBaseUrl;

    public NetworkHelper() {
        App.getInstance().getAppComponent().inject(this);
    }

    @NonNull
    public Observable<List<Repository>> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        final Trending trending = new RetroJsoup.Builder()
                .url(String.format("%strending/%s?since=%s", mBaseUrl, language, timeSpan))
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
                .url(String.format("%strending/", mBaseUrl))
                .client(mClient)
                .build()
                .create(Trending.class);

        return trending.getLanguages()
                .toList().toObservable();
    }

    @NonNull
    public Observable<List<TimeSpan>> getTimeSpans() {
        final Trending trending = new RetroJsoup.Builder()
                .url(String.format("%strending/", mBaseUrl))
                .client(mClient)
                .build()
                .create(Trending.class);

        return trending.getTimeSpans()
                .toList().toObservable();
    }

    @NonNull
    private Observable<Repository> setLanguageAndTimeSpan(@NonNull Repository item, @NonNull String language, @NonNull String timeSpan) {
        item.setLanguage(language);
        item.setTimeSpan(timeSpan);
        item.setOrder(Integer.parseInt(item.getStarsToday().split("\\s+")[0].replaceAll(",", "")));
        return Observable.just(item);
    }
}
