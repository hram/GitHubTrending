package hram.githubtrending;

import android.support.annotation.NonNull;

import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.List;

import hram.githubtrending.data.model.Language;
import hram.githubtrending.data.model.Repository;
import hram.githubtrending.data.model.TimeSpan;
import hram.githubtrending.data.network.Trending;

/**
 * @author Evgeny Khramov
 */

public class BaseTest {

    List<Repository> getRepositories(@NonNull String url) {
        final List<Repository>[] response = new List[1];

        final Trending trending = new RetroJsoup.Builder()
                .url(url)
                .build()
                .create(Trending.class);

        trending.getRepositories()
                .toList()
                .subscribe(list -> response[0] = list);

        return response[0];
    }

    List<Language> getLanguages(@NonNull String url) {
        final List<Language>[] response = new List[1];

        final Trending trending = new RetroJsoup.Builder()
                .url(url)
                .build()
                .create(Trending.class);

        trending.getLanguages()
                .toList()
                .subscribe(list -> response[0] = list);

        return response[0];
    }

    List<TimeSpan> getTimeSpans(@NonNull String url) {
        final List<TimeSpan>[] response = new List[1];

        final Trending trending = new RetroJsoup.Builder()
                .url(url)
                .build()
                .create(Trending.class);

        trending.getTimeSpans()
                .toList()
                .subscribe(list -> response[0] = list);

        return response[0];
    }
}
