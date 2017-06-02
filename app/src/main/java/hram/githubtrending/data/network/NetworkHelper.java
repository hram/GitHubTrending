package hram.githubtrending.data.network;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import io.reactivex.Observable;

/**
 * @author Evgeny Khramov
 */

public class NetworkHelper {

    public Observable<List<Repository>> getRepositories(@NonNull String language, @NonNull String timeSpan) {
        final Trending trending = new RetroJsoup.Builder()
                .url(String.format("https://github.com/trending/%s?since=%s", language, timeSpan))
                //.client(okHttpClient)
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
                .url("https://github.com/trending/")
                //.client(okHttpClient)
                .build()
                .create(Trending.class);

        return trending.getLanguages()
                .toList().toObservable();
    }
    
    @NonNull
    public Observable<List<TimeSpan>> getTimeSpans() {
        final Trending trending = new RetroJsoup.Builder()
                .url("https://github.com/trending/")
                //.client(okHttpClient)
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
